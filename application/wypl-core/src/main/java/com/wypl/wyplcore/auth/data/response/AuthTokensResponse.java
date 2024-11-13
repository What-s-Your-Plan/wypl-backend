package com.wypl.wyplcore.auth.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wypl.googleoauthclient.data.response.GoogleTokenResponse;

import lombok.Builder;

@Builder
public record AuthTokensResponse(
	@JsonProperty("member_id")
	long memberId,
	@JsonProperty("access_token")
	String accessToken,
	@JsonProperty("refresh_token")
	String refreshToken
) {
	public static AuthTokensResponse of(long memberId, GoogleTokenResponse googleTokenResponse) {
		return AuthTokensResponse.builder()
			.memberId(memberId)
			.accessToken(googleTokenResponse.accessToken())
			.refreshToken(googleTokenResponse.refreshToken())
			.build();
	}
}
