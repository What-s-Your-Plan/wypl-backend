package com.wypl.wyplcore.review.exception;

import com.wypl.common.exception.WyplException;

public class ReviewException extends WyplException {
	public ReviewException(ReviewErrorCode serverErrorCode) {
		super(serverErrorCode);
	}
}
