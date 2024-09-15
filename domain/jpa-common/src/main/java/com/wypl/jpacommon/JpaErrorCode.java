package com.wypl.jpacommon;

import com.wypl.common.exception.ServerErrorCode;

import lombok.Getter;

@Getter
public enum JpaErrorCode implements ServerErrorCode {
	ALREADY_DELETED_ENTITY(400, "JPA_001", "이미 삭제된 데이터입니다"),
	NON_DELETED_ENTITY(400, "JPA_002", "삭제되지 않은 데이터는 복구할 수 없습니다.");
	private final int statusCode;
	private final String errorCode;
	private final String message;

	private JpaErrorCode(int statusCode, String errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
}
