package com.wypl.wyplcore.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.jpamemberdomain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberServiceImpl {
	private final MemberRepository memberRepository;

	@Transactional
	public void deleteMember(AuthMember authMember) {
		memberRepository.deleteById(authMember.id());
	}
}
