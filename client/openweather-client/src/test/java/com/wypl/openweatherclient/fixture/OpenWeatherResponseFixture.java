package com.wypl.openweatherclient.fixture;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wypl.openweatherclient.data.OpenWeatherResponse;

/**
 * 도시_언어_도씨
 * KOREA_KR_CELSIUS
 * KOREA_EN_FAHRENHEIT
 */
public enum OpenWeatherResponseFixture {
	KOREA_KR_CELSIUS(
			List.of(new OpenWeatherResponse.WeatherResponse(500, "Rain", "실 비")),
			new OpenWeatherResponse.MainResponse(18.74f, 19.69f, 18.66f),
			new OpenWeatherResponse.SysResponse(1728855542, 1728896209),
			1728905264
	),
	KOREA_EN_CELSIUS(
			List.of(new OpenWeatherResponse.WeatherResponse(500, "Rain", "light rain")),
			new OpenWeatherResponse.MainResponse(18.74f, 19.69f, 18.66f),
			new OpenWeatherResponse.SysResponse(1728855542, 1728896209),
			1728905264
	),
	KOREA_KR_FAHRENHEIT(
			List.of(new OpenWeatherResponse.WeatherResponse(500, "Rain", "실 비")),
			new OpenWeatherResponse.MainResponse(291.89f, 292.84f, 291.81f),
			new OpenWeatherResponse.SysResponse(1728855542, 1728896209),
			1728905264
	),
	KOREA_EN_FAHRENHEIT(
			List.of(new OpenWeatherResponse.WeatherResponse(500, "Rain", "light rain")),
			new OpenWeatherResponse.MainResponse(291.89f, 292.84f, 291.81f),
			new OpenWeatherResponse.SysResponse(1728855542, 1728896209),
			1728905264
	);

	private final List<OpenWeatherResponse.WeatherResponse> weather;
	private final OpenWeatherResponse.MainResponse main;
	private final OpenWeatherResponse.SysResponse sys;
	private final long dateTime;

	OpenWeatherResponseFixture(
			List<OpenWeatherResponse.WeatherResponse> weather,
			OpenWeatherResponse.MainResponse main,
			OpenWeatherResponse.SysResponse sys,
			long dateTime
	) {
		this.weather = weather;
		this.main = main;
		this.sys = sys;
		this.dateTime = dateTime;
	}

	public ResponseEntity<OpenWeatherResponse> to2xxResponse() {
		return ResponseEntity.ok(new OpenWeatherResponse(weather, main, sys, dateTime));
	}

	public ResponseEntity<OpenWeatherResponse> to3xxResponse() {
		return ResponseEntity.status(300).build();
	}

	public ResponseEntity<OpenWeatherResponse> to5xxResponse() {
		return ResponseEntity.status(500).build();
	}
}
