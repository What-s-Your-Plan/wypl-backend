package com.wypl.openweatherclient;

import com.wypl.openweatherclient.data.OpenWeatherCond;
import com.wypl.openweatherclient.data.OpenWeatherResponse;

public interface OpenWeatherClient {
	OpenWeatherResponse fetchWeather(OpenWeatherCond cond);
}
