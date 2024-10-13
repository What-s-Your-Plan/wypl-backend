package com.wypl.openweatherclient.data;

import com.wypl.openweatherclient.type.WeatherRegion;

public record OpenWeatherCond(
		WeatherRegion city,
		boolean isMetric,
		boolean isLangKr
) {
	public static OpenWeatherCond fromCity(
			final WeatherRegion city
	) {
		return new OpenWeatherCond(city, true, true);
	}

	public static OpenWeatherCond of(
			final WeatherRegion city,
			final boolean isMetric,
			final boolean isLangKr
	) {
		return new OpenWeatherCond(city, isMetric, isLangKr);
	}
}
