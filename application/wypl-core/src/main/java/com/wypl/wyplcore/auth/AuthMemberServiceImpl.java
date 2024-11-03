package com.wypl.wyplcore.auth;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.wypl.googleoauthclient.GoogleOAuthClient;
import com.wypl.googleoauthclient.data.AuthMemberService;
import com.wypl.googleoauthclient.data.response.GoogleTokenValidationResponse;
import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.jpamemberdomain.member.OauthProvider;
import com.wypl.jpamemberdomain.member.domain.SocialMember;
import com.wypl.jpamemberdomain.member.SocialMemberRepository;
import com.wypl.jpamemberdomain.member.exception.MemberErrorCode;
import com.wypl.jpamemberdomain.member.exception.MemberException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthMemberServiceImpl implements AuthMemberService {
	private final GoogleOAuthClient googleOAuthClient;
	private final SocialMemberRepository socialMemberRepository;

	@Override
	public AuthMember getValidatedMemberId(String accessToken) {
		GoogleTokenValidationResponse response = googleOAuthClient.validateToken(accessToken);
		Optional<SocialMember> optionalSocialMember
			= socialMemberRepository.findByOauthProviderAndOauthId(OauthProvider.GOOGLE, response.userId());
		if (optionalSocialMember.isEmpty()) {
			throw new MemberException(MemberErrorCode.NO_SUCH_MEMBER);
		}
		return AuthMember.of(optionalSocialMember.get().getId(), accessToken);
	}
}
