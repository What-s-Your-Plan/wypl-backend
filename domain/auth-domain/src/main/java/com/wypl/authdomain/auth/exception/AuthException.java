package com.wypl.authdomain.auth.exception;

import com.wypl.common.exception.WyplException;

public class AuthException extends WyplException {
	public AuthException(AuthErrorCode serverErrorCode)	{
		super(serverErrorCode);
	}
}
