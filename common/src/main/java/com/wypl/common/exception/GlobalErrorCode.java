package com.wypl.common.exception;

public enum GlobalErrorCode implements ServerErrorCode {
	INTERNAL_SERVER_ERROR(500, "GLOBAL_001", "알 수 없는 서버의 오류입니다."),
	;

	private final int statusCode;
	private final String errorCode;
	private final String message;

	GlobalErrorCode(int statusCode, String errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}

	@Override
	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public int getStatusCode() {
		return statusCode;
	}
}
