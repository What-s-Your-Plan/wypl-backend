package com.wypl.wyplcore.facade;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wypl.authdomain.auth.service.AuthDomainServiceImpl;
import com.wypl.googleoauthclient.GoogleOAuthClient;
import com.wypl.googleoauthclient.data.response.GoogleTokenResponse;
import com.wypl.googleoauthclient.data.response.GoogleUserInfoResponse;
import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.googleoauthclient.exception.GoogleOAuthErrorCode;
import com.wypl.googleoauthclient.exception.GoogleOAuthException;
import com.wypl.wyplcore.auth.data.response.AuthTokensResponse;
import com.wypl.wyplcore.member.service.MemberServiceImpl;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Component
public class AuthMemberFacadeImpl implements AuthMemberFacade {
	private final GoogleOAuthClient googleOAuthClient;
	private final AuthDomainServiceImpl authDomainService;
	private final MemberServiceImpl memberService;

	@Override
	@Transactional
	public AuthTokensResponse generateToken(final String provider, final String code) {
		GoogleTokenResponse googleTokenResponse = googleOAuthClient.fetchGoogleOAuthToken(code);

		GoogleUserInfoResponse googleUserInfoResponse = googleOAuthClient.fetchUserInfo(
			googleTokenResponse.accessToken());

		long memberId = memberService.findMemberIdOrSaveMember(googleTokenResponse.accessToken(), googleUserInfoResponse);

		authDomainService.saveToken(googleTokenResponse.accessToken(), googleTokenResponse.refreshToken());

		return AuthTokensResponse.of(memberId, googleTokenResponse);
	}

	@Override
	@Transactional
	public AuthTokensResponse reissueToken(final String accessToken, final String refreshToken) {
		if (isInvalidRefreshToken(accessToken, refreshToken)) {
			throw new GoogleOAuthException(GoogleOAuthErrorCode.NOT_AUTHORIZATION_MEMBER);
		}

		GoogleTokenResponse googleTokenResponse = googleOAuthClient.fetchRefreshGoogleOAuthToken(refreshToken);

		authDomainService.deleteToken(accessToken);
		authDomainService.saveToken(googleTokenResponse.accessToken(), refreshToken);

		return AuthTokensResponse.of(googleTokenResponse.accessToken(), refreshToken);
	}

	@Override
	@Transactional
	public void logout(AuthMember authMember) {
		deleteToken(authMember);
	}

	@Override
	@Transactional
	public void quitMember(AuthMember authMember) {
		// Todo : 회원 탈퇴 로직 논의
		deleteToken(authMember);
		memberService.deleteMember(authMember);
	}

	private void deleteToken(AuthMember authMember) {
		authDomainService.deleteToken(authMember.accessToken());
	}

	private boolean isInvalidRefreshToken(String accessToken, String refreshToken) {
		return refreshToken.isEmpty() || !refreshToken.equals(authDomainService.getRefreshToken(accessToken));
	}
}
