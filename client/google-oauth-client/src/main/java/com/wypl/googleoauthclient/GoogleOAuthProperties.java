package com.wypl.googleoauthclient;

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
	// 서버말고 yml만 따로 빌드할 수 있다 -> 편의성 굿
	private String clientId; // client-id
	private String clientSecret;
	private String redirectUri;
	private String accessTokenUri;
}
