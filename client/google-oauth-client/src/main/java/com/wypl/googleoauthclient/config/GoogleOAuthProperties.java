package com.wypl.googleoauthclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PACKAGE)
@Getter
@ConfigurationProperties(prefix = "google.oauth")
@Configuration
public class GoogleOAuthProperties {
	private String clientId;        // client-id
	private String clientSecret;
	private String redirectUri;
	private String accessTokenUri;
}
