package com.wypl.jpamemberdomain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.wypl.jpamemberdomain.member.data.MemberDto;
import com.wypl.jpamemberdomain.member.domain.Member;
import com.wypl.jpamemberdomain.member.repository.MemberRepository;

@DataJpaTest
public class MemberRepositoryTest {
	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("Member를 저장한다.")
	@Nested
	class memberSaveTest {
		private MemberDto memberDtoMock;
		private Member member;

		@BeforeEach
		void beforeEach() {
			memberDtoMock = MemberDto.builder()
				.email("email")
				.nickname("nickname")
				.birthday(LocalDate.of(1999, 1, 15))
				.profileImage("profileImage")
				.build();

			member = Member.of(memberDtoMock);
		}

		@DisplayName("Member를 성공적으로 저장한다.")
		@Test
		void saveSuccessTest() {
			// When
			Member result = memberRepository.save(member);
			Member findMember = memberRepository.findById(result.getMemberId()).get();

			// Then
			assertThat(result.getMemberId()).isEqualTo(findMember.getMemberId());
			assertThat(result.getEmail()).isEqualTo(memberDtoMock.getEmail());
			assertThat(result.getNickname()).isEqualTo(memberDtoMock.getNickname());
			assertThat(result.getBirthday()).isEqualTo(memberDtoMock.getBirthday());
			assertThat(result.getProfileImage()).isEqualTo(memberDtoMock.getProfileImage());
		}

		@DisplayName("중복된 Member 저장에 실패한다.")
		@Test
		void duplicatedMemberSaveTest() {
			// Given
			Member duplicatedMember = Member.of(memberDtoMock);

			// When
			memberRepository.save(member);

			// Then
			assertThatThrownBy(() -> memberRepository.save(duplicatedMember))
				.isInstanceOf(DataIntegrityViolationException.class)
				.hasMessageContaining("Unique index or primary key violation");
		}
	}
}

