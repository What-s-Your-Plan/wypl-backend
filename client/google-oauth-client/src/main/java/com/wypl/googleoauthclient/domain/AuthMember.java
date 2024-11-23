package com.wypl.googleoauthclient.domain;

public record AuthMember(
	long id,
	String accessToken
) {
	public static AuthMember of(long id, String accessToken) {
		return new AuthMember(id, accessToken);
	}
}