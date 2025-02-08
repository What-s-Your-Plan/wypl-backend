package com.wypl.authdomain.auth.exception;

import com.wypl.common.exception.ServerErrorCode;

import lombok.Getter;

@Getter
public enum AuthErrorCode implements ServerErrorCode {
	INVALID_TOKEN(400, "AUTH_001", "올바르지 않은 토큰입니다."),
	EXPIRED_TOKEN(401, "AUTH_002", "만료된 토큰입니다.");

	private final int statusCode;
	private final String errorCode;
	private final String message;

	AuthErrorCode(int statusCode, String errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
}
