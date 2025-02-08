package com.wypl.wyplcore.member.handler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.wypl.wyplcore.member.data.MemberEventDto;
import com.wypl.wyplcore.token.service.TokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MemberEventHandler {
	private final TokenService tokenService;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
	public void deleteToken(MemberEventDto memberEventDto) {
		tokenService.deleteToken(memberEventDto.accessToken());
	}
}
