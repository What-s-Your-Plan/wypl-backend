package com.wypl.wyplcore.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wypl.googleoauthclient.GoogleOAuthClient;
import com.wypl.googleoauthclient.data.response.GoogleTokenResponse;
import com.wypl.googleoauthclient.data.response.GoogleUserInfoResponse;
import com.wypl.jpamemberdomain.member.domain.SocialMember;
import com.wypl.jpamemberdomain.member.repository.SocialMemberRepository;
import com.wypl.jpamemberdomain.member.utils.SocialMemberRepositoryUtils;
import com.wypl.wyplcore.auth.data.response.AuthTokensResponse;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthServiceImpl {
	private final GoogleOAuthClient googleOAuthClient;
	private final SocialMemberRepository socialMemberRepository;

	@Transactional
	public AuthTokensResponse generateToken(final String provider, final String code) {
		GoogleTokenResponse googleTokenResponse = googleOAuthClient.fetchGoogleOAuthToken(code);

		GoogleUserInfoResponse googleUserInfoResponse = googleOAuthClient.fetchUserInfo(
			googleTokenResponse.accessToken());

		SocialMember socialMember = SocialMemberRepositoryUtils.getSocialMember(socialMemberRepository, googleUserInfoResponse.id());

		return AuthTokensResponse.of(socialMember.getId(), googleTokenResponse);
	}
}

