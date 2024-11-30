package com.wypl.jpamemberdomain.member.exception;

import com.wypl.common.exception.ServerErrorCode;

import lombok.Getter;

@Getter
public enum MemberErrorCode implements ServerErrorCode {
	NO_SUCH_MEMBER(400, "MEMBER_001", "존재하지 않는 회원입니다.");

	private final int statusCode;
	private final String errorCode;
	private final String message;

	MemberErrorCode(int statusCode, String errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
}
