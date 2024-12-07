package com.wypl.wyplcore.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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
import com.wypl.jpamemberdomain.member.domain.SocialMember;
import com.wypl.jpamemberdomain.member.repository.SocialMemberRepository;
import com.wypl.wyplcore.auth.data.response.AuthTokensResponse;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
	@InjectMocks
	private AuthServiceImpl authService;
	@Mock
	private GoogleOAuthClient googleOAuthClient;
	@Mock
	private SocialMemberRepository socialMemberRepository;
	@Mock
	private AuthDomainServiceImpl authDomainService;

	@DisplayName("로그인 및 회원가입 로직을 테스트한다.")
	@Nested
	class generateTokenTest {
		private GoogleTokenResponse mockGoogleTokenResponse;
		private GoogleUserInfoResponse mockGoogleUserInfoResponse;

		@BeforeEach
		void setUp() {
			mockGoogleTokenResponse = new GoogleTokenResponse(
				"accessToken",
				3600,
				"refreshToken",
				"scope",
				"idToken",
				"tokenType"
			);

			mockGoogleUserInfoResponse = new GoogleUserInfoResponse(
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
		}

		@DisplayName("이미 가입한 회원인 경우, 로그인에 성공한다.")
		@Test
		void signInTest() {
			//Given
			SocialMember mockSocialMember = SocialMember.builder()
				.id(1L)
				.oauthProvider(OauthProvider.GOOGLE)
				.oauthId("mockOauthId")
				.build();

			given(socialMemberRepository.existsByOauthProviderAndOauthId(any(OauthProvider.class), anyString()))
				.willReturn(true);

			given(socialMemberRepository.findByOauthProviderAndOauthId(any(OauthProvider.class), anyString()))
				.willReturn(Optional.of(mockSocialMember));

			// When
			AuthTokensResponse result = authService.generateToken("provider", "Authroization code");

			// Then
			verify(authDomainService).saveToken(mockGoogleTokenResponse.accessToken(),
				mockGoogleTokenResponse.refreshToken());
			assertThat(result.memberId()).isEqualTo(mockSocialMember.getId());
			assertThat(result.accessToken()).isEqualTo(mockGoogleTokenResponse.accessToken());
			assertThat(result.refreshToken()).isEqualTo(mockGoogleTokenResponse.refreshToken());
		}

		@DisplayName("신규 회원인 경우, 회원가입에 성공한다.")
		@Test
		void signUpTest() {
			// Given
			given(socialMemberRepository.existsByOauthProviderAndOauthId(any(OauthProvider.class), anyString()))
				.willReturn(false);

			given(googleOAuthClient.fetchBirthday(anyString()))
				.willReturn(LocalDate.now());

			given(authDomainService.saveAuthData(any(MemberDto.class), any(SocialMemberDto.class)))
				.willReturn(2L);

			// When
			AuthTokensResponse result = authService.generateToken("provider", "Authroization code");

			// Then
			verify(authDomainService).saveToken(mockGoogleTokenResponse.accessToken(),
				mockGoogleTokenResponse.refreshToken());
			assertThat(result.memberId()).isEqualTo(2L);
			assertThat(result.accessToken()).isEqualTo(mockGoogleTokenResponse.accessToken());
			assertThat(result.refreshToken()).isEqualTo(mockGoogleTokenResponse.refreshToken());
		}
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
			given(authDomainService.getRefreshToken(anyString()))
				.willReturn(refreshToken);

			// When & Then
			assertThatThrownBy(() -> authService.reissueToken("accessToken", "refreshToken"))
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

			given(authDomainService.getRefreshToken(anyString()))
				.willReturn("refreshToken");

			given(googleOAuthClient.fetchRefreshGoogleOAuthToken(anyString()))
				.willReturn(mockGoogleTokenResponse);

			// When
			AuthTokensResponse result = authService.reissueToken("accessToken", "refreshToken");

			// Then
			verify(authDomainService).deleteToken(anyString());
			verify(authDomainService).saveToken(anyString(), anyString());
			assertThat(result.accessToken()).isEqualTo(mockGoogleTokenResponse.accessToken());
			assertThat(result.refreshToken()).isEqualTo("refreshToken");
		}
	}

	@DisplayName("로그아웃 로직이 정상적으로 동작한다.")
	@Test
	void deleteToken() {
		// Given
		AuthMember mockAuthMember = AuthMember.of(
			1L,
			"accessToken"
		);

		// When
		authService.logout(mockAuthMember);

		// Then
		verify(authDomainService).deleteToken(anyString());
	}
}