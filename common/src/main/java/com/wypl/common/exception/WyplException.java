package com.wypl.common.exception;

import lombok.Getter;

@Getter
public class WyplException extends RuntimeException {
	private final int statusCode;
	private final String message;
	private final String errorCode;

	public WyplException(ServerErrorCode serverErrorCode) {
		this.statusCode = serverErrorCode.getStatusCode();
		this.message = serverErrorCode.getMessage();
		this.errorCode = serverErrorCode.getErrorCode();
	}
}
