package com.wypl.wyplcore.auth;

import static org.assertj.core.api.Assertions.*;
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

import com.wypl.googleoauthclient.GoogleOAuthClient;
import com.wypl.googleoauthclient.data.response.GoogleTokenValidationResponse;
import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.jpamemberdomain.member.OauthProvider;
import com.wypl.jpamemberdomain.member.SocialMemberRepository;
import com.wypl.jpamemberdomain.member.domain.SocialMember;
import com.wypl.jpamemberdomain.member.exception.MemberErrorCode;
import com.wypl.jpamemberdomain.member.exception.MemberException;

@ExtendWith(MockitoExtension.class)
class AuthMemberServiceImplTest {
	@InjectMocks
	private AuthMemberServiceImpl authMemberService;
	@Mock
	private GoogleOAuthClient googleOAuthClient;
	@Mock
	private SocialMemberRepository socialMemberRepository;

	@DisplayName("현재의 토큰을 검증한다.")
	@Nested
	class GetValidatedMemberId {

		private static final String OAUTH_ID = "OAUTH_ID";

		@BeforeEach
		void setUp() {
			given(googleOAuthClient.validateToken(anyString()))
				.willReturn(new GoogleTokenValidationResponse(OAUTH_ID, "sjhjack@naver.com"));
		}

		@DisplayName("AuthMember 정상적으로 생성된다.")
		@Test
		void success() {
			// Given
			String accessToken = "accessToken";
			// Todo : Fixture 만들어서 분리하기!
			SocialMember mockSocialMember = SocialMember.builder()
				.id(1L)
				.oauthProvider(OauthProvider.GOOGLE)
				.oauthId(OAUTH_ID)
				.build();

			given(socialMemberRepository.findByOauthProviderAndOauthId(any(OauthProvider.class), anyString()))
				.willReturn(Optional.of(mockSocialMember));

			// When
			AuthMember authMember = authMemberService.getValidatedMemberId(accessToken);

			// Then
			assertThat(authMember).isNotNull();
			assertThat(authMember.id()).isEqualTo(1L);
			assertThat(authMember.accessToken()).isEqualTo(accessToken);
		}

		@DisplayName("토큰으로 멤버 정보를 조회하지 못한다.")
		@Test
		void socialMemberEmptyTest() {
			// Given
			given(socialMemberRepository.findByOauthProviderAndOauthId(any(OauthProvider.class), anyString()))
				.willReturn(Optional.empty());

			// When & Then
			assertThatThrownBy(() -> authMemberService.getValidatedMemberId(anyString()))
				.isInstanceOf(MemberException.class)
				.hasMessageContaining(MemberErrorCode.NO_SUCH_MEMBER.getMessage());
		}
	}
}