package com.wypl.redistokendomain.exception;

import com.wypl.common.exception.ServerErrorCode;

import lombok.Getter;

@Getter
public enum RedisTokenErrorCode implements ServerErrorCode {
	TOKEN_IS_NOT_EXISTED(400, "REDIS_TOKEN_001", "존재하지 않는 토큰입니다."),
	;

	private final int statusCode;
	private final String errorCode;
	private final String message;

	RedisTokenErrorCode(int statusCode, String errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
}
