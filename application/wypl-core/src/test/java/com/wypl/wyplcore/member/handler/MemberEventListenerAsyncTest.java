package com.wypl.wyplcore.member.handler;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.jpamemberdomain.member.repository.MemberRepository;
import com.wypl.wyplcore.member.service.MemberService;
import com.wypl.wyplcore.token.service.TokenService;

@SpringBootTest
class MemberEventListenerAsyncTest {
	@Autowired
	private MemberService memberService;
	@MockBean
	private TokenService tokenService;
	@MockBean
	private MemberRepository memberRepository;

	@DisplayName("회원 탈퇴 후 Refresh Token을 비동기로 삭제한다.")
	@Test
	void deleteToken_asynchronously() throws InterruptedException {
		// Given
		AuthMember mockAuthMember = AuthMember.of(
			1L,
			"accessToken"
		);

		// When
		memberService.deleteMember(mockAuthMember);
		Thread.sleep(3000);

		// Then
		verify(memberRepository).deleteById(anyLong());
		verify(tokenService).deleteToken(anyString());
	}
}