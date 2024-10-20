package com.wypl.openweatherclient.fixture;

import java.util.ArrayList;
import java.util.List;

import com.wypl.openweatherclient.data.OpenWeatherCond;
import com.wypl.openweatherclient.type.WeatherRegion;

public enum OpenWeatherFixture {
	KOREA(WeatherRegion.KOREA, true, true),
	WEST_USA(WeatherRegion.WEST_USA, false, false),
	EAST_USA(WeatherRegion.EAST_USA, false, false),
	ENGLAND(WeatherRegion.ENGLAND, false, true),
	;

	private final WeatherRegion weatherRegion;
	private final boolean isLangKr;
	private final boolean isMetric;

	OpenWeatherFixture(WeatherRegion weatherRegion, boolean isLangKr, boolean isMetric) {
		this.weatherRegion = weatherRegion;
		this.isLangKr = isLangKr;
		this.isMetric = isMetric;
	}

	public List<String> urlParams() {
		List<String> params = new ArrayList<>();
		if (isLangKr) {
			params.add("lang=kr");
		} else {
			params.add("lang");
		}
		if (isMetric) {
			params.add("units=metric");
		} else {
			params.add("units");
		}
		return params;
	}

	public boolean isLangKr() {
		return isLangKr;
	}

	public boolean isMetric() {
		return isMetric;
	}

	public WeatherRegion getWeatherRegion() {
		return weatherRegion;
	}

	public OpenWeatherCond toOpenWeatherCond() {
		return new OpenWeatherCond(weatherRegion, isMetric, isLangKr);
	}
}
