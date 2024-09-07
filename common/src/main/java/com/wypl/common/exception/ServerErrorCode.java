package com.wypl.common.exception;

public interface ServerErrorCode {
	int getStatusCode();

	String getMessage();

	String getErrorCode();
}
