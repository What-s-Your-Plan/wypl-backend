package com.wypl.authdomain.auth.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.wypl.jpamemberdomain.member.data.MemberDto;
import com.wypl.jpamemberdomain.member.data.SocialMemberDto;
import com.wypl.jpamemberdomain.member.domain.Member;
import com.wypl.jpamemberdomain.member.domain.SocialMember;
import com.wypl.jpamemberdomain.member.repository.MemberRepository;
import com.wypl.jpamemberdomain.member.repository.SocialMemberRepository;
import com.wypl.redistokendomain.TokenRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthDomainServiceImpl {
	private final MemberRepository memberRepository;
	private final SocialMemberRepository socialMemberRepository;
	private final RedisTemplate<byte[], byte[]> redisTokenTemplate;
	private final TokenRepository tokenRepository;

	public boolean checkExistsToken(String accessToken) {
		byte[] refreshToken = redisTokenTemplate.opsForValue().get(accessToken);
		return refreshToken != null;
	}

	public long saveAuthData(MemberDto memberDto, SocialMemberDto socialMemberDto) {
		Member newMember = memberRepository.save(Member.of(memberDto));
		SocialMember socialMember = socialMemberRepository.save(SocialMember.of(newMember, socialMemberDto));
		return socialMember.getId();
	}

	public void saveToken(String accessToken, String refreshToken) {
		tokenRepository.saveToken(accessToken, refreshToken);
	}
}
