// package com.wypl.authdomain.auth.service;
//
// import static org.assertj.core.api.Assertions.*;
// import static org.mockito.BDDMockito.*;
//
// import java.time.LocalDate;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.junit.jupiter.params.ParameterizedTest;
// import org.junit.jupiter.params.provider.EnumSource;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
//
// import com.wypl.authdomain.auth.service.fixture.MemberFixture;
// import com.wypl.jpamemberdomain.member.OauthProvider;
// import com.wypl.jpamemberdomain.member.data.MemberSaveDto;
// import com.wypl.jpamemberdomain.member.data.SocialMemberSaveDto;
// import com.wypl.jpamemberdomain.member.domain.Member;
// import com.wypl.jpamemberdomain.member.domain.SocialMember;
// import com.wypl.jpamemberdomain.member.repository.MemberRepository;
// import com.wypl.jpamemberdomain.member.repository.SocialMemberRepository;
// import com.wypl.redistokendomain.TokenRepository;
//
// @ExtendWith(MockitoExtension.class)
// class AuthDomainServiceImplTest {
// 	@InjectMocks
// 	private AuthDomainServiceImpl authDomainService;
// 	@Mock
// 	private MemberRepository memberRepository;
// 	@Mock
// 	private SocialMemberRepository socialMemberRepository;
// 	@Mock
// 	private TokenRepository tokenRepository;
//
// 	@DisplayName("유저의 Auth 데이터를 저장한다.")
// 	@ParameterizedTest
// 	@EnumSource
// 	void saveAuthDateTest(MemberFixture fixture) {
// 		// Given
// 		MemberSaveDto memberSaveDtoMock = MemberSaveDto.builder()
// 			.email("email")
// 			.nickname("nickname")
// 			.birthday(LocalDate.of(1999, 1, 15))
// 			.profileImage("profileImage")
// 			.build();
//
// 		SocialMemberSaveDto socialMemberSaveDtoMock = SocialMemberSaveDto.builder()
// 			.oauthProvider(OauthProvider.GOOGLE)
// 			.oauthId("oauthId")
// 			.build();
//
// 		Member memberMock = fixture.toMember();
//
// 		SocialMember socialMemberMock = fixture.toSocialMember();
//
// 		given(memberRepository.save(any(Member.class)))
// 			.willReturn(memberMock);
//
// 		given(socialMemberRepository.save(any(SocialMember.class)))
// 			.willReturn(socialMemberMock);
//
// 		// When
// 		long result = authDomainService.saveAuthData(memberSaveDtoMock, socialMemberSaveDtoMock);
//
// 		// Then
// 		assertThat(result).isEqualTo(socialMemberMock.getId());
// 	}
//
// 	@DisplayName("Redis에 존재하는 토큰인지 확인한다.")
// 	@Test
// 	void checkExistsTokenTest() {
// 		// When
// 		authDomainService.checkExistsToken(anyString());
//
// 		// Then
// 		verify(tokenRepository).checkExistsToken(anyString());
// 	}
//
// 	@DisplayName("Redis에 토큰을 저장한다.")
// 	@Test
// 	void saveTokenTest() {
// 		// When
// 		authDomainService.saveToken(anyString(), anyString());
//
// 		// Then
// 		verify(tokenRepository).saveToken(anyString(), anyString());
// 	}
//
// 	@DisplayName("Redis에서 Access Token으로 Refresh Token 값을 가져온다.")
// 	@Test
// 	void getRefreshTokenTest() {
// 		// When
// 		authDomainService.getRefreshToken(anyString());
//
// 		// Then
// 		verify(tokenRepository).getRefreshToken(anyString());
// 	}
//
// 	@DisplayName("Redis에 저장된 토큰 정보를 삭제한다.")
// 	@Test
// 	void deleteToken() {
// 		// When
// 		authDomainService.deleteToken(anyString());
//
// 		// Then
// 		verify(tokenRepository).deleteToken(anyString());
// 	}
// }