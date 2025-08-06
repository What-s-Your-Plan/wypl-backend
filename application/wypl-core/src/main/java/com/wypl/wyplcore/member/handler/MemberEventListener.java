package com.wypl.wyplcore.member.handler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.wypl.wyplcore.member.data.MemberEventDto;
import com.wypl.wyplcore.token.service.TokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MemberEventListener {
	private final TokenService tokenService;

	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	// @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
	public void deleteToken(MemberEventDto memberEventDto) {
		try {
			System.out.println("deleteToken Event start~~");
			tokenService.deleteToken(memberEventDto.accessToken());
			System.out.println("deleteToken Event end~~");
		} catch (Exception e) {
			System.out.println(e.getMessage()) ;
		}
	}
}
