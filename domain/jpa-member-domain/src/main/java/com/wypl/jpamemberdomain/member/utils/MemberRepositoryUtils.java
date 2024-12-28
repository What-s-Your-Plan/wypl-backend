package com.wypl.jpamemberdomain.member.utils;

import com.wypl.common.exception.CallConstructorException;
import com.wypl.jpamemberdomain.member.domain.Member;
import com.wypl.jpamemberdomain.member.exception.MemberErrorCode;
import com.wypl.jpamemberdomain.member.exception.MemberException;
import com.wypl.jpamemberdomain.member.repository.MemberRepository;

import lombok.Generated;

public class MemberRepositoryUtils {

	@Generated
	private MemberRepositoryUtils() {
		throw new CallConstructorException();
	}

	public static Member findById(
		final MemberRepository repository,
		final long id
	) {
		return repository.findById(id)
			.orElseThrow(() -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER));
	}
}
