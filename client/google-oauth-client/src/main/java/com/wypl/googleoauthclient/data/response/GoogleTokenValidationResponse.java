package com.wypl.googleoauthclient.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleTokenValidationResponse(
	@JsonProperty("user_id")
	String userId,
	String email
) {
}
