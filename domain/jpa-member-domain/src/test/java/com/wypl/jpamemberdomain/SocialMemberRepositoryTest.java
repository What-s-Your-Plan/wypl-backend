package com.wypl.jpamemberdomain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DuplicateKeyException;

import com.wypl.jpamemberdomain.member.OauthProvider;
import com.wypl.jpamemberdomain.member.data.MemberSaveDto;
import com.wypl.jpamemberdomain.member.data.SocialMemberSaveDto;
import com.wypl.jpamemberdomain.member.domain.Member;
import com.wypl.jpamemberdomain.member.domain.SocialMember;
import com.wypl.jpamemberdomain.member.repository.SocialMemberRepository;

@DataJpaTest
public class SocialMemberRepositoryTest {
	@Autowired
	private SocialMemberRepository socialMemberRepository;

	@DisplayName("SocialMember를 저장한다.")
	@Nested
	class socialMemberSaveTest {
		private SocialMemberSaveDto socialMemberSaveDtoMock;
		private Member member;
		private SocialMember socialMember;

		@BeforeEach
		void beforeEach() {
			MemberSaveDto memberSaveDtoMock = MemberSaveDto.builder()
				.email("email")
				.nickname("nickname")
				.birthday(LocalDate.of(1999, 1, 15))
				.profileImage("profileImage")
				.build();

			socialMemberSaveDtoMock = SocialMemberSaveDto.builder()
				.oauthProvider(OauthProvider.GOOGLE)
				.oauthId("oauthId")
				.build();

			member = Member.of(memberSaveDtoMock);

			socialMember = SocialMember.of(member, socialMemberSaveDtoMock);
		}

		@DisplayName("SocialMember를 성공적으로 저장한다.")
		@Test
		void saveSuccessTest() {
			// When
			SocialMember result = socialMemberRepository.save(socialMember);
			SocialMember findSocialMember = socialMemberRepository.findById(result.getId()).get();

			// Then
			assertThat(result).isEqualTo(findSocialMember);
		}

		@DisplayName("중복된 SocialMember 저장에 실패한다.")
		@Test
		void duplicatedMemberSaveTest() {
			// Given
			SocialMember duplicatedSocialMember = SocialMember.of(member, socialMemberSaveDtoMock);

			// When
			socialMemberRepository.save(socialMember);

			// Then
			assertThatThrownBy(() -> socialMemberRepository.save(duplicatedSocialMember))
				.isInstanceOf(DuplicateKeyException.class)
				.hasMessageContaining("A different object with the same identifier value was already associated with the session");
		}
	}
}
