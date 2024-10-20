package com.wypl.openweatherclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "open-weather")
@Getter
@Setter
public class OpenWeatherProperties {
	private String key;
	private String baseUrl;
}
