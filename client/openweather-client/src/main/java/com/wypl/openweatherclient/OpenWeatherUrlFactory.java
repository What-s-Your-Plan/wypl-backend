package com.wypl.openweatherclient;

import com.wypl.openweatherclient.type.WeatherRegion;

public class OpenWeatherUrlFactory {

	private final StringBuilder url;

	private OpenWeatherUrlFactory(String baseUrl, String key) {
		this.url = new StringBuilder(baseUrl).append("?appid=").append(key);
	}

	public static OpenWeatherUrlFactory create(String baseUrl, String key) {
		return new OpenWeatherUrlFactory(baseUrl, key);
	}

	public OpenWeatherUrlFactory weatherRegion(WeatherRegion weatherRegion) {
		url.append("&q=").append(weatherRegion.getCityEn());
		return this;
	}

	public OpenWeatherUrlFactory isLangKr(boolean isLangKr) {
		url.append("&lang=");
		if (isLangKr) {
			url.append("kr");
		}
		return this;
	}

	public OpenWeatherUrlFactory isMetric(boolean isMetric) {
		url.append("&units=");
		if (isMetric) {
			url.append("metric");
		}
		return this;
	}

	public String build() {
		return url.toString();
	}
}
