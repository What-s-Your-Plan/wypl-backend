package com.wypl.jpamemberdomain.member.utils;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wypl.jpamemberdomain.member.exception.MemberErrorCode;
import com.wypl.jpamemberdomain.member.exception.MemberException;
import com.wypl.jpamemberdomain.member.fixture.MemberFixture;
import com.wypl.jpamemberdomain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberRepositoryUtilsTest {

	private final MemberRepository repository;

	public MemberRepositoryUtilsTest(@Mock MemberRepository repository) {
		this.repository = repository;
	}

	@DisplayName("FindById 테스트")
	@Nested
	class FindById {

		private long memberId;

		@BeforeEach
		public void setUp() {
			memberId = ThreadLocalRandom.current().nextLong();
		}

		@DisplayName("Member 조회에 성공한다.")
		@ParameterizedTest
		@EnumSource(MemberFixture.class)
		void successTest(MemberFixture fixture) {
			/* Given */
			given(repository.findById(anyLong()))
				.willReturn(Optional.of(fixture.toMember()));

			/* When & Then */
			assertThatCode(() -> MemberRepositoryUtils.findById(repository, memberId))
				.doesNotThrowAnyException();
		}

		@DisplayName("Member가 존재하지 않으면 예외를 던진다.")
		@Test
		void failedDueToNoSuchMemberTest() {
			/* When & Then */
			assertThatThrownBy(() -> MemberRepositoryUtils.findById(repository, memberId))
				.isInstanceOf(MemberException.class)
				.hasMessageContaining(MemberErrorCode.NO_SUCH_MEMBER.getMessage());
		}
	}
}