package com.wypl.jpacalendardomain.calendar.utils;

import static com.wypl.jpacalendardomain.calendar.CalendarFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpacalendardomain.calendar.exception.CalendarErrorCode;
import com.wypl.jpacalendardomain.calendar.exception.CalendarException;
import com.wypl.jpacalendardomain.calendar.repository.CalendarRepository;

@DataJpaTest
class CalendarRepositoryUtilsTest {
	@Autowired
	private CalendarRepository repository;

	@DisplayName("Calendar 조회 Util")
	@Nested
	class FindById {

		private Calendar calendar;

		@BeforeEach
		public void setUp() {
			calendar = wyplProject.toCalendar();
			repository.save(calendar);
		}

		@DisplayName("Calendar 조회에 성공한다.")
		@Test
		void success() {
			/* When & Then */
			assertThatCode(() -> CalendarRepositoryUtils.findById(repository, calendar.getId()))
				.doesNotThrowAnyException();
		}

		@DisplayName("Calendar 조회시 존재하지 않으면 예외를 던진다.")
		@Test
		void noSuchCalender() {
			assertThatThrownBy(() -> CalendarRepositoryUtils.findById(repository, calendar.getId() + 1_000L))
				.isInstanceOf(CalendarException.class)
				.hasMessageContaining(CalendarErrorCode.NO_SUCH_CALENDAR.getMessage());
		}
	}
}