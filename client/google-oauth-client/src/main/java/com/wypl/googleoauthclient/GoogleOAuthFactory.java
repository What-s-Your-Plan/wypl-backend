// package com.wypl.googleoauthclient;
//
// import org.springframework.util.LinkedMultiValueMap;
// import org.springframework.util.MultiValueMap;
//
// public abstract class GoogleOAuthFactory {
// 	protected final MultiValueMap<String, String> multiValueMap;
//
// 	protected GoogleOAuthFactory(GoogleOAuthProperties properties) {
// 		this.multiValueMap = new LinkedMultiValueMap<>();
//
// 		multiValueMap.add("client_id", properties.getClientId());
// 		multiValueMap.add("client_secret", properties.getClientSecret());
// 	}
//
//
// 	public static GoogleOAuthFactory create(GoogleOAuthProperties properties) {
// 		return new GoogleOAuthFactory(properties);
// 	}
// }
