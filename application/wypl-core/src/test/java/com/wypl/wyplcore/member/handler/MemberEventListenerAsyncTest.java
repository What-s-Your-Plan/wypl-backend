package com.wypl.wyplcore.member.handler;

import static org.mockito.Mockito.*;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.jpamemberdomain.member.repository.MemberRepository;
import com.wypl.wyplcore.member.data.MemberEventDto;
import com.wypl.wyplcore.member.service.MemberService;
import com.wypl.wyplcore.token.service.TokenService;

@SpringBootTest
class MemberEventListenerAsyncTest {
	// @Mock
	@MockBean
	private TokenService tokenService;
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private MemberService memberService;
	@MockBean
	private MemberRepository memberRepository;

	@DisplayName("Token을 비동기로 삭제한다.")
	@Transactional
	@Test
	void deleteToken_asynchronously() throws InterruptedException {
		// Given
		MemberEventDto memberEventDtoMock = new MemberEventDto("accessToken");
		CountDownLatch countDownLatch = new CountDownLatch(1);

		Mockito.doAnswer(invocation -> {
			countDownLatch.await();
			return null;
		}).when(tokenService).deleteToken(Mockito.anyString());

		// When
		applicationEventPublisher.publishEvent(memberEventDtoMock);

		// Then
		Mockito.verify(tokenService, Mockito.never()).deleteToken(Mockito.anyString());
		countDownLatch.countDown();
		Thread.sleep(3000);
		Mockito.verify(tokenService).deleteToken(Mockito.anyString());
	}

	@DisplayName("Token을 비동기로 삭제한다.")
	@Test
	void deleteToken_asynchronously2() throws InterruptedException {
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