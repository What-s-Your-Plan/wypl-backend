package com.wypl.wyplcore.auth.service;

import org.springframework.stereotype.Component;

import com.wypl.googleoauthclient.GoogleOAuthClient;
import com.wypl.googleoauthclient.data.response.GoogleTokenValidationResponse;
import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.googleoauthclient.service.AuthMemberService;
import com.wypl.jpamemberdomain.member.domain.SocialMember;
import com.wypl.jpamemberdomain.member.repository.SocialMemberRepository;
import com.wypl.jpamemberdomain.member.utils.SocialMemberRepositoryUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthMemberServiceImpl implements AuthMemberService {
	private final GoogleOAuthClient googleOAuthClient;
	private final SocialMemberRepository socialMemberRepository;

	@Override
	public AuthMember getValidatedMemberId(String accessToken) {
		GoogleTokenValidationResponse response = googleOAuthClient.validateToken(accessToken);

		SocialMember socialMember = SocialMemberRepositoryUtils.getSocialMember(
			socialMemberRepository,
			response.userId());

		return AuthMember.of(socialMember.getId(), accessToken);
	}
}
