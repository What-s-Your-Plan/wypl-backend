package com.wypl.googleoauthclient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class GoogleOAuthParamFactory {
	private final MultiValueMap<String, String> multiValueMap;

	private GoogleOAuthParamFactory(String clientId, String clientSecret, String redirectUri) {
		this.multiValueMap = new LinkedMultiValueMap<>();

		multiValueMap.add("client_id", clientId);
		multiValueMap.add("client_secret", clientSecret);
		multiValueMap.add("redirect_uri", redirectUri);
	}

	public static GoogleOAuthParamFactory create(String clientId, String clientSecret, String redirectUri) {
		return new GoogleOAuthParamFactory(clientId, clientSecret, redirectUri);
	}

	public GoogleOAuthParamFactory code(String code) {
		String decode = URLDecoder.decode(code, StandardCharsets.UTF_8);
		multiValueMap.add("code", decode);
		return this;
	}

	public GoogleOAuthParamFactory grantType(String grantType) {
		multiValueMap.add("grant_type", grantType);
		return this;
	}

	public MultiValueMap<String, String> build() {
		return multiValueMap;
	}
}
