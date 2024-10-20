package com.wypl.googleoauthclient.exception;

import com.wypl.common.exception.ServerErrorCode;

import lombok.Getter;

@Getter
public enum GoogleOAuthErrorCode implements ServerErrorCode {
	BAD_REQUEST(400, "GOOGLE_OAUTH_001", ""),
	MALFORMED(400, "GOOGLE_OAUTH_002", ""),
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
