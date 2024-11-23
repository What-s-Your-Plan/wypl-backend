package com.wypl.googleoauthclient.exception;

import com.wypl.common.exception.WyplException;

public class GoogleOAuthException extends WyplException {
	public GoogleOAuthException(GoogleOAuthErrorCode serverErrorCode) {
		super(serverErrorCode);
	}
}
