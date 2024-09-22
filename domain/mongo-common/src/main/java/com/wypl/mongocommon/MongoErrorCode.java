package com.wypl.mongocommon;

import com.wypl.common.exception.ServerErrorCode;

import lombok.Getter;

@Getter
public enum MongoErrorCode implements ServerErrorCode {
	ALREADY_DELETED_ENTITY(400, "JPA_001", "이미 삭제된 요소는 삭제할 수 없습니다."),
	NON_DELETED_ENTITY(400, "JPA_002", "삭제되지 않은 요소는 복구할 수 없습니다.");
	private final int statusCode;
	private final String errorCode;
	private final String message;

	MongoErrorCode(int statusCode, String errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
}
