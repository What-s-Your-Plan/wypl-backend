package com.wypl.openweatherclient;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.wypl.openweatherclient.data.OpenWeatherCond;
import com.wypl.openweatherclient.data.OpenWeatherResponse;
import com.wypl.openweatherclient.exception.OpenWeatherErrorCode;
import com.wypl.openweatherclient.exception.OpenWeatherException;
import com.wypl.openweatherclient.type.WeatherRegion;

import lombok.RequiredArgsConstructor;

@EnableConfigurationProperties(OpenWeatherProperties.class)
@RequiredArgsConstructor
@Component
public class OpenWeatherClientImpl implements OpenWeatherClient {

	private final RestTemplate restTemplate;
	private final OpenWeatherProperties properties;

	public OpenWeatherResponse fetchWeather(OpenWeatherCond cond) {
		String url = getUrl(cond);
		ResponseEntity<OpenWeatherResponse> response = restTemplate.getForEntity(
				url,
				OpenWeatherResponse.class
		);
		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		}
		if (response.getStatusCode().is5xxServerError()) {
			throw new OpenWeatherException(OpenWeatherErrorCode.INTERNAL_SERVER_ERROR);
		}
		throw new OpenWeatherException(OpenWeatherErrorCode.INVALID_OPEN_WEATHER_REQUEST);
	}

	private String getUrl(OpenWeatherCond cond) {
		StringBuilder url = new StringBuilder(properties.getBaseUrl())
				.append("?appid=")
				.append(properties.getKey());

		addParamByCity(url, cond.city());
		addParamByLang(url, cond.isLangKr());
		addParamByUnits(url, cond.isMetric());

		return url.toString();
	}

	private void addParamByCity(StringBuilder url, WeatherRegion region) {
		url.append("&q=").append(region.getCityEn());
	}

	private void addParamByLang(StringBuilder url, boolean isLangKr) {
		if (isLangKr) {
			url.append("&lang=kr");
		}
	}

	private void addParamByUnits(StringBuilder url, boolean isMetric) {
		if (isMetric) {
			url.append("&units=metric");
		}
	}
}
