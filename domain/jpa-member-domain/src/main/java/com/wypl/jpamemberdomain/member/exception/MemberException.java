package com.wypl.jpamemberdomain.member.exception;

import com.wypl.common.exception.WyplException;

public class MemberException extends WyplException {
	public MemberException(MemberErrorCode serverErrorCode) {
		super(serverErrorCode);
	}
}
