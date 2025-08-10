package com.wypl.wyplcore.member.handler;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.RecordApplicationEvents;

import com.wypl.wyplcore.member.data.MemberEventDto;
import com.wypl.wyplcore.token.service.TokenService;

@RecordApplicationEvents
@ExtendWith(MockitoExtension.class)
class MemberEventListenerTest {
	@InjectMocks
	private MemberEventListener memberEventListener;
	@Mock
	private TokenService tokenService;

	@DisplayName("이벤트 리스너를 통해 Token을 삭제한다.")
	@Test
	void deleteTokenTest() {
		// Given
		MemberEventDto memberEventDtoMock = new MemberEventDto("accessToken");

		// When
		memberEventListener.deleteToken(memberEventDtoMock);

		// Then
		verify(tokenService).deleteToken(anyString());
	}
}