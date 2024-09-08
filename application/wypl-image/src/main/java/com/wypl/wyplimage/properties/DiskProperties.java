package com.wypl.wyplimage.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PACKAGE)
@Getter
@Configuration
@ConfigurationProperties(prefix = "working.directory")
public class DiskProperties {
	private String absolutePath;
}