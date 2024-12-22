package com.wypl.jpamemberdomain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wypl.jpamemberdomain.member.OauthProvider;
import com.wypl.jpamemberdomain.member.data.MemberDto;
import com.wypl.jpamemberdomain.member.data.SocialMemberDto;
import com.wypl.jpamemberdomain.member.domain.Member;
import com.wypl.jpamemberdomain.member.domain.SocialMember;
import com.wypl.jpamemberdomain.member.repository.SocialMemberRepository;

@SpringBootTest
public class SocialMemberRepositoryTest {
	@Autowired
	private SocialMemberRepository socialMemberRepository;

	@DisplayName("SocialMember를 저장한다.")
	@Test
	void saveSocialMemberTest() {
		// Given
		MemberDto memberDtoMock = MemberDto.builder()
			.email("email")
			.nickname("nickname")
			.birthday(LocalDate.of(1999, 1, 15))
			.profileImage("profileImage")
			.build();

		SocialMemberDto socialMemberDtoMock = SocialMemberDto.builder()
			.oauthProvider(OauthProvider.GOOGLE)
			.oauthId("oauthId")
			.build();

		// When
		SocialMember result = socialMemberRepository.save(SocialMember.of(Member.of(memberDtoMock), socialMemberDtoMock));
		SocialMember findSocialMember = socialMemberRepository.findById(result.getId()).get();

		// Then
		assertThat(result.getId()).isEqualTo(findSocialMember.getId());
		assertThat(result.getMember().getMemberId()).isEqualTo(findSocialMember.getMember().getMemberId());
		assertThat(result.getOauthId()).isEqualTo(findSocialMember.getOauthId());
		assertThat(result.getOauthProvider()).isEqualTo(findSocialMember.getOauthProvider());
	}
}
