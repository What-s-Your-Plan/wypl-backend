package com.wypl.googleoauthclient.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleUserInfoResponse(
	String id,
	String email,
	@JsonProperty("verified_email")
	String verifiedEmail,
	String name,
	@JsonProperty("given_name")
	String givenName,
	String picture
) {
}
