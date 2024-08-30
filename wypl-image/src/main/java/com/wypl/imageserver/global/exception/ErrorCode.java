package com.wypl.imageserver.global.exception;

public interface ErrorCode {
	int getStatusCode();

	String getErrorCode();

	String getMessage();
}
