package com.wypl.wyplcore.review.exception;

import com.wypl.common.exception.ServerErrorCode;

import lombok.Getter;

@Getter
public enum ReviewErrorCode implements ServerErrorCode {
	EMPTY_CONTENTS(500, "REVIEW_001", "작성된 내용이 없습니다."),
	INVALID_TITLE(500, "REVIEW_002", "제목의 길이가 올바르지 않습니다."),
	NO_SUCH_REVIEW(500, "REVIEW_003", "존재하지 않는 회고입니다.")
	;

	private final int statusCode;
	private final String errorCode;
	private final String message;

	ReviewErrorCode(int statusCode, String errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
}
