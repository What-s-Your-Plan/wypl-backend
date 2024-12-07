package com.wypl.wyplcore.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wypl.authdomain.auth.service.AuthDomainServiceImpl;
import com.wypl.googleoauthclient.GoogleOAuthClient;
import com.wypl.googleoauthclient.data.response.GoogleTokenResponse;
import com.wypl.googleoauthclient.data.response.GoogleUserInfoResponse;
import com.wypl.jpamemberdomain.member.OauthProvider;
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
		}
	}
}