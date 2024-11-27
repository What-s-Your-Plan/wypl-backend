package com.wypl.redistokendomain.exception;

import com.wypl.common.exception.WyplException;

public class RedisTokenException extends WyplException {
	public RedisTokenException(RedisTokenErrorCode serverErrorCode) {
		super(serverErrorCode);
	}
}
