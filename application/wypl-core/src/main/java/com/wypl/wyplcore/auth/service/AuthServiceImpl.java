package com.wypl.wyplcore.auth.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wypl.authdomain.auth.service.AuthDomainServiceImpl;
import com.wypl.googleoauthclient.GoogleOAuthClient;
import com.wypl.googleoauthclient.data.response.GoogleTokenResponse;
import com.wypl.googleoauthclient.data.response.GoogleUserInfoResponse;
import com.wypl.jpamemberdomain.member.OauthProvider;
import com.wypl.jpamemberdomain.member.data.MemberDto;
import com.wypl.jpamemberdomain.member.data.SocialMemberDto;
import com.wypl.jpamemberdomain.member.repository.MemberRepository;
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
	private final MemberRepository memberRepository;
	private final AuthDomainServiceImpl authDomainService;

	@Transactional
	public AuthTokensResponse generateToken(final String provider, final String code) {
		GoogleTokenResponse googleTokenResponse = googleOAuthClient.fetchGoogleOAuthToken(code);

		GoogleUserInfoResponse googleUserInfoResponse = googleOAuthClient.fetchUserInfo(
			googleTokenResponse.accessToken());

		long memberId = findMemberIdAfterSaveMember(googleTokenResponse.accessToken(), googleUserInfoResponse);

		authDomainService.saveToken(googleTokenResponse.accessToken(), googleTokenResponse.refreshToken());

		return AuthTokensResponse.of(memberId, googleTokenResponse);
	}

	private long findMemberIdAfterSaveMember(String accessToken, GoogleUserInfoResponse googleUserInfoResponse) {
		if(!socialMemberRepository.existsByOauthProviderAndOauthId(OauthProvider.GOOGLE, googleUserInfoResponse.id())) {
			LocalDate birthday = googleOAuthClient.fetchBirthday(accessToken);

			MemberDto memberDto = MemberDto.builder()
				.email(googleUserInfoResponse.email())
				.birthday(birthday)
				.nickname(googleUserInfoResponse.name())
				.profileImage(googleUserInfoResponse.picture())
				.build();

			SocialMemberDto socialMemberDto = SocialMemberDto.builder()
				.oauthProvider(OauthProvider.GOOGLE)
				.oauthId(googleUserInfoResponse.id())
				.build();

			return authDomainService.saveAuthData(memberDto, socialMemberDto);
		}

		return SocialMemberRepositoryUtils.getSocialMember(socialMemberRepository, googleUserInfoResponse.id()).getId();

	}
}

