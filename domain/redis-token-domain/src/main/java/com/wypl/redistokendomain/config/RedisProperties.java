package com.wypl.redistokendomain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PACKAGE)
@Getter
@ConfigurationProperties(prefix = "spring.data.redis")
@Configuration
public class RedisProperties {
	private String host;
	private int port;
	private String password;
}
