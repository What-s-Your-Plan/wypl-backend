package com.wypl.wyplcore.member.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.jpamemberdomain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {
	@InjectMocks
	private MemberServiceImpl memberService;
	@Mock
	private MemberRepository memberRepository;

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
	}
}
