package com.wypl.wyplcore.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentCaptor.*;
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
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.RecordApplicationEvents;

import com.wypl.googleoauthclient.GoogleOAuthClient;
import com.wypl.googleoauthclient.data.response.GoogleUserInfoResponse;
import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.jpamemberdomain.member.OauthProvider;
import com.wypl.jpamemberdomain.member.domain.Member;
import com.wypl.jpamemberdomain.member.domain.SocialMember;
import com.wypl.jpamemberdomain.member.repository.MemberRepository;
import com.wypl.jpamemberdomain.member.repository.SocialMemberRepository;
import com.wypl.wyplcore.member.data.MemberEventDto;
import com.wypl.wyplcore.member.fixture.MemberFixture;

@RecordApplicationEvents
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
	@InjectMocks
	private MemberService memberService;
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private GoogleOAuthClient googleOAuthClient;
	@Mock
	private SocialMemberRepository socialMemberRepository;
	@Mock
	private ApplicationEventPublisher applicationEventPublisher;

	@DisplayName("Member를 정상적으로 삭제한다.")
	@Test
	void quitMemberTest() {
		// Given
		AuthMember mockAuthMember = AuthMember.of(
			1L,
			"accessToken"
		);

		// When
		memberService.deleteMember(mockAuthMember);

		// Then
		verify(memberRepository).deleteById(anyLong());

		// ArgumentCaptor를 사용하여 이벤트 캡처 (파라미터 캡처)
		ArgumentCaptor<MemberEventDto> eventCaptor = forClass(MemberEventDto.class);
		verify(applicationEventPublisher).publishEvent(eventCaptor.capture());

		// 캡처된 이벤트 검증 (파라미터 검증)
		MemberEventDto capturedEvent = eventCaptor.getValue();
		assertThat(capturedEvent.accessToken()).isEqualTo("accessToken");
	}

	@DisplayName("로그인 및 회원가입 로직을 테스트한다.")
	@Nested
	class generateTokenTest {
		private GoogleUserInfoResponse mockGoogleUserInfoResponse;
		private String accessToken;

		@BeforeEach
		void setUp() {
			mockGoogleUserInfoResponse = new GoogleUserInfoResponse(
				"id",
				"email",
				"verifiedEmail",
				"name",
				"givenName",
				"familyName"
			);

			accessToken = "access_token";
		}

		@DisplayName("이미 가입한 회원인 경우, 로그인에 성공한다.")
		@Test
		void signInTest() {
			// Given
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
			long result = memberService.findMemberIdOrSaveMember(accessToken, mockGoogleUserInfoResponse);

			// Then
			assertThat(result).isEqualTo(1L);
		}

		@DisplayName("신규 회원인 경우, 회원가입에 성공한다.")
		@ParameterizedTest
		@EnumSource
		void signUpTest(MemberFixture fixture) {
			// Given
			Member memberMock = fixture.toMember();

			SocialMember socialMemberMock = fixture.toSocialMember();

			given(memberRepository.save(any(Member.class)))
				.willReturn(memberMock);

			given(socialMemberRepository.save(any(SocialMember.class)))
				.willReturn(socialMemberMock);

			given(socialMemberRepository.existsByOauthProviderAndOauthId(any(OauthProvider.class), anyString()))
				.willReturn(false);

			given(googleOAuthClient.fetchBirthday(anyString()))
				.willReturn(LocalDate.now());

			// When
			long result = memberService.findMemberIdOrSaveMember(accessToken, mockGoogleUserInfoResponse);

			// Then
			assertThat(result).isEqualTo(socialMemberMock.getId());
		}
	}
}
