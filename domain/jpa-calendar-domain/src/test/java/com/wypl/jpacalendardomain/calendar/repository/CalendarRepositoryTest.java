package com.wypl.jpacalendardomain.calendar.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wypl.jpacalendardomain.calendar.CalendarFixture;
import com.wypl.jpacalendardomain.calendar.domain.Calendar;

import jakarta.persistence.EntityManager;

@DataJpaTest
class CalendarRepositoryTest {

	private final CalendarRepository repository;

	@Autowired
	public CalendarRepositoryTest(CalendarRepository repository) {
		this.repository = repository;
	}

	@DisplayName("Calendar 저장 시 자동 생성 필드를 제외하고 내부 값이 동일한지 검증한다.")
	@ParameterizedTest
	@EnumSource(CalendarFixture.class)
	void saveTest(CalendarFixture fixture) {
		/* Given */
		Calendar calendar = fixture.toCalendar();

		/* When */
		Calendar savedCalendar = repository.save(calendar);

		/* Then */
		assertThat(savedCalendar)
			.usingRecursiveAssertion()
			.ignoringFields("created", "lastModified")
			.isEqualTo(calendar);
	}

	@DisplayName("Calendar 설정과 조회 테스트")
	@Nested
	class CalendarSetupTest {
		private final EntityManager em;

		private Calendar savedCalendar;

		@Autowired
		public CalendarSetupTest(EntityManager em) {
			this.em = em;
		}

		@BeforeEach
		void setUp() {
			savedCalendar = repository.save(CalendarFixture.WYPL_PROJECT.toCalendar());

			em.flush();
			em.clear();
		}

		@DisplayName("ID로 Member를 조회하면 저장된 Member를 반환한다.")
		@Test
		void findByIdTest() {
			/* Given */
			Long calendarId = savedCalendar.getId();

			/* When */
			Calendar foundCalendar = repository.findById(calendarId)
				.orElseThrow();

			/* Then */
			assertThat(foundCalendar)
				.usingRecursiveComparison()
				.ignoringFields("created", "lastModified",
					"memberCalendars", "scheduleInfos")    // 기존에 `null`로 존재하다가 new ArrayList<>()가 초기화
				.isEqualTo(savedCalendar);
		}
	}
}