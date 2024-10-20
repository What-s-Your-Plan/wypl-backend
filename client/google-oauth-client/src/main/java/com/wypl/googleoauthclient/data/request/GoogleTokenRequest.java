package com.wypl.googleoauthclient.data.request;

import lombok.Builder;

@Builder
public record GoogleTokenRequest(
	String clientId,
	String clientSecret,
	String code,
	String grantType,
	String redirectUri
) {
}
