package com.wypl.openweatherclient;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.wypl.openweatherclient.config.OpenWeatherProperties;
import com.wypl.openweatherclient.data.OpenWeatherCond;
import com.wypl.openweatherclient.data.OpenWeatherResponse;
import com.wypl.openweatherclient.exception.OpenWeatherErrorCode;
import com.wypl.openweatherclient.exception.OpenWeatherException;

import lombok.RequiredArgsConstructor;

@EnableConfigurationProperties(OpenWeatherProperties.class)
@RequiredArgsConstructor
@Component
public class OpenWeatherClientImpl implements OpenWeatherClient {

	private final RestTemplate restTemplate;
	private final OpenWeatherProperties properties;

	public OpenWeatherResponse fetchWeather(OpenWeatherCond cond) {
		String url = OpenWeatherUrlFactory.create(properties.getBaseUrl(), properties.getKey())
				.weatherRegion(cond.city())
				.isLangKr(cond.isLangKr())
				.isMetric(cond.isMetric())
				.build();

		ResponseEntity<OpenWeatherResponse> response = restTemplate.getForEntity(
				url,
				OpenWeatherResponse.class
		);

		HttpStatusCode httpStatusCode = response.getStatusCode();
		if (httpStatusCode.is2xxSuccessful()) {
			return response.getBody();
		}
		if (httpStatusCode.is5xxServerError()) {
			throw new OpenWeatherException(OpenWeatherErrorCode.INTERNAL_SERVER_ERROR);
		}
		throw new OpenWeatherException(OpenWeatherErrorCode.INVALID_OPEN_WEATHER_REQUEST);
	}
}
