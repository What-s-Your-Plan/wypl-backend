package com.wypl.wyplimage.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AccessLevel;
import lombok.Setter;

@Setter(AccessLevel.PACKAGE)
@Configuration
@ConfigurationProperties(prefix = "image")
public class ImageResponseMessage {
	private String upload;

	public String upload() {
		return upload;
	}
}
