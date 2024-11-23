package com.wypl.googleoauthclient.exception;

import com.wypl.common.exception.ServerErrorCode;

import lombok.Getter;

@Getter
public enum GoogleOAuthErrorCode implements ServerErrorCode {
	BAD_REQUEST(400, "GOOGLE_OAUTH_001", "권한이 없습니다."),
	MALFORMED(400, "GOOGLE_OAUTH_002", "올바르지 않은 토큰입니다."),
	INVALID_TOKEN(400, "GOOGLE_OAUTH_003", "올바르지 않은 값입니다."),
	NOT_AUTHORIZATION_MEMBER(400, "GOOGLE_OAUTH_004", "인증되지 않은 회원입니다."),
	REFRESH_TOKEN(401, "GOOGLE_OAUTH_005", "만료된 토큰입니다.")
	;

	private final int statusCode;
	private final String errorCode;
	private final String message;

	GoogleOAuthErrorCode(int statusCode, String errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
}
