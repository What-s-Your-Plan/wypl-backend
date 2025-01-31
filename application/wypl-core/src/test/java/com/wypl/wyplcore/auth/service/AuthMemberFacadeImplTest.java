package com.wypl.wyplcore.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wypl.googleoauthclient.GoogleOAuthClient;
import com.wypl.googleoauthclient.data.response.GoogleTokenResponse;
import com.wypl.googleoauthclient.data.response.GoogleUserInfoResponse;
import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.googleoauthclient.exception.GoogleOAuthErrorCode;
import com.wypl.googleoauthclient.exception.GoogleOAuthException;
import com.wypl.wyplcore.auth.data.response.AuthTokensResponse;
import com.wypl.wyplcore.facade.AuthMemberFacadeImpl;
import com.wypl.wyplcore.member.service.MemberService;
import com.wypl.wyplcore.token.service.TokenService;

@ExtendWith(MockitoExtension.class)
class AuthMemberFacadeImplTest {
	@InjectMocks
	private AuthMemberFacadeImpl authMemberFacade;
	@Mock
	private GoogleOAuthClient googleOAuthClient;
	@Mock
	private TokenService tokenService;
	@Mock
	private MemberService memberService;

	@DisplayName("토큰을 성공적으로 발행한다.")
	@Test
	void generateTokenTest() {
		// Given
		GoogleTokenResponse mockGoogleTokenResponse = new GoogleTokenResponse(
						"accessToken",
						3600,
						"refreshToken",
						"scope",
						"idToken",
						"tokenType"
					);

		GoogleUserInfoResponse mockGoogleUserInfoResponse = new GoogleUserInfoResponse(
						"id",
						"email",
						"verifiedEmail",
						"name",
						"givenName",
						"familyName"
					);

		given(googleOAuthClient.fetchGoogleOAuthToken(anyString()))
			.willReturn(mockGoogleTokenResponse);

		given(googleOAuthClient.fetchUserInfo(anyString()))
			.willReturn(mockGoogleUserInfoResponse);

		given(memberService.findMemberIdOrSaveMember(anyString(), any(GoogleUserInfoResponse.class)))
			.willReturn(1L);

		// When
		AuthTokensResponse result = authMemberFacade.generateToken("provider", "Authroization code");

		// Then
		verify(tokenService).saveToken(anyString(), anyString());
		assertThat(result.memberId()).isEqualTo(1L);
		assertThat(result.accessToken()).isEqualTo(mockGoogleTokenResponse.accessToken());
		assertThat(result.refreshToken()).isEqualTo(mockGoogleTokenResponse.refreshToken());
	}

	@DisplayName("토큰 재발행 로직을 테스트한다.")
	@Nested
	class reissueTokenTest {
		@DisplayName("유효하지 않은 리프레시 토큰으로 인해 인증되지 않은 회원 오류를 던진다.")
		@ParameterizedTest
		@ValueSource(strings = {"", "invalid Refresh Token"})
		@NullSource
		void invalidRefreshTokenTest(String refreshToken) {
			// Given
			given(tokenService.getRefreshToken(anyString()))
				.willReturn(refreshToken);

			// When & Then
			assertThatThrownBy(() -> authMemberFacade.reissueToken("accessToken", "refreshToken"))
				.isInstanceOf(GoogleOAuthException.class)
				.hasMessageContaining(GoogleOAuthErrorCode.NOT_AUTHORIZATION_MEMBER.getMessage());
		}

		@DisplayName("토큰을 정상적으로 재발행한다.")
		@Test
		void reissueSuccessTest() {
			// Given
			GoogleTokenResponse mockGoogleTokenResponse = new GoogleTokenResponse(
				"accessToken",
				3600,
				"refreshToken",
				"scope",
				"idToken",
				"tokenType"
			);

			given(tokenService.getRefreshToken(anyString()))
				.willReturn("refreshToken");

			given(googleOAuthClient.fetchRefreshGoogleOAuthToken(anyString()))
				.willReturn(mockGoogleTokenResponse);

			// When
			AuthTokensResponse result = authMemberFacade.reissueToken("accessToken", "refreshToken");

			// Then
			verify(tokenService).deleteToken(anyString());
			verify(tokenService).saveToken(anyString(), anyString());
			assertThat(result.accessToken()).isEqualTo(mockGoogleTokenResponse.accessToken());
			assertThat(result.refreshToken()).isEqualTo("refreshToken");
		}
	}

	@DisplayName("로그아웃 로직이 정상적으로 동작한다.")
	@Test
	void logoutTest() {
		// Given
		AuthMember mockAuthMember = AuthMember.of(
			1L,
			"accessToken"
		);

		// When
		authMemberFacade.logout(mockAuthMember);

		// Then
		verify(tokenService).deleteToken(anyString());
	}

	@DisplayName("회원탈퇴 로직이 정상적으로 동작한다.")
	@Test
	void quitMemberTest() {
		// Given
		AuthMember mockAuthMember = AuthMember.of(
			1L,
			"accessToken"
		);

		// When
		authMemberFacade.quitMember(mockAuthMember);

		// Then
		verify(tokenService).deleteToken(anyString());
		verify(memberService).deleteMember(any(AuthMember.class));
	}
}