package com.wypl.wyplcore.auth.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wypl.authdomain.auth.service.AuthDomainServiceImpl;
import com.wypl.googleoauthclient.GoogleOAuthClient;
import com.wypl.googleoauthclient.data.response.GoogleTokenResponse;
import com.wypl.googleoauthclient.data.response.GoogleUserInfoResponse;
import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.googleoauthclient.exception.GoogleOAuthErrorCode;
import com.wypl.googleoauthclient.exception.GoogleOAuthException;
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

	@Transactional
	public AuthTokensResponse reissueToken(final String accessToken, final String refreshToken) {
		if (isInvalidRefreshToken(accessToken, refreshToken)) {
			throw new GoogleOAuthException(GoogleOAuthErrorCode.NOT_AUTHORIZATION_MEMBER);
		}

		// FIXME : member Id를 같이 보내야할까? 안 보내도 될 것 같은데.. redis에 access_token : {refresh_token, member_id}로 저장?

		GoogleTokenResponse googleTokenResponse = googleOAuthClient.fetchRefreshGoogleOAuthToken(refreshToken);

		authDomainService.deleteToken(accessToken);
		authDomainService.saveToken(googleTokenResponse.accessToken(), refreshToken);

		return AuthTokensResponse.of(googleTokenResponse.accessToken(), refreshToken);
	}

	@Transactional
	public void logout(AuthMember authMember) {
		deleteToken(authMember);
	}

	@Transactional
	public void quitMember(AuthMember authMember) {
		// Todo : 회원 탈퇴 로직 논의
		deleteToken(authMember);
		deleteMember(authMember);
	}

	private void deleteToken(AuthMember authMember) {
		authDomainService.deleteToken(authMember.accessToken());
	}

	private void deleteMember(AuthMember authMember) {
		memberRepository.deleteById(authMember.id());
	}

	private boolean isInvalidRefreshToken(String accessToken, String refreshToken) {
		return refreshToken.isEmpty() || !refreshToken.equals(authDomainService.getRefreshToken(accessToken));
	}

	private long findMemberIdAfterSaveMember(String accessToken, GoogleUserInfoResponse googleUserInfoResponse) {
		if (isNewMember(googleUserInfoResponse)) {
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

	private boolean isNewMember(GoogleUserInfoResponse googleUserInfoResponse) {
		return !socialMemberRepository.existsByOauthProviderAndOauthId(OauthProvider.GOOGLE,
			googleUserInfoResponse.id());
	}
}

