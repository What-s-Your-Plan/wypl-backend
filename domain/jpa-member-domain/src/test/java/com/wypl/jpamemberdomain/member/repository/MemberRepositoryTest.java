package com.wypl.jpamemberdomain.member.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wypl.jpamemberdomain.member.domain.Member;
import com.wypl.jpamemberdomain.member.fixture.MemberFixture;

import jakarta.persistence.EntityManager;

@DataJpaTest
class MemberRepositoryTest {

	private final MemberRepository repository;

	public MemberRepositoryTest(@Autowired MemberRepository repository) {
		this.repository = repository;
	}

	@DisplayName("Member 저장 시 자동 생성 필드를 제외하고 내부 값이 동일한지 검증한다.")
	@ParameterizedTest
	@EnumSource(MemberFixture.class)
	void saveTest(MemberFixture fixture) {
		/* Given */
		Member member = fixture.toMember();

		/* When */
		Member savedMember = repository.save(member);

		/* Then */
		assertThat(savedMember)
			.usingRecursiveComparison()
			.ignoringFields("createdAt", "modifiedAt")    // JPA 관련 설정
			.isEqualTo(member);
	}

	@DisplayName("Member 설정과 조회 테스트")
	@Nested
	class MemberSetupTest {
		private final EntityManager em;

		private Member savedMember;

		@Autowired
		public MemberSetupTest(EntityManager em) {
			this.em = em;
		}

		@BeforeEach
		void setUp() {
			savedMember = repository.save(MemberFixture.JEONG_HOON.toMember());

			em.flush();
			em.clear();
		}

		@DisplayName("ID로 Member를 조회하면 저장된 Member를 반환한다.")
		@Test
		void findByIdTest() {
			/* Given */
			Long memberId = savedMember.getMemberId();

			/* When */
			Member foundMember = repository.findById(memberId).orElseThrow();

			/* Then */
			assertThat(foundMember)
				.usingRecursiveComparison()
				.ignoringFields("created", "lastModified")
				.isEqualTo(savedMember);
		}
	}
}