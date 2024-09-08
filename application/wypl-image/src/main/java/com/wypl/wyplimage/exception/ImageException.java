package com.wypl.wyplimage.exception;

import com.wypl.common.exception.ServerErrorCode;
import com.wypl.common.exception.WyplException;

public class ImageException extends WyplException {
	public ImageException(ServerErrorCode serverErrorCode) {
		super(serverErrorCode);
	}
}
