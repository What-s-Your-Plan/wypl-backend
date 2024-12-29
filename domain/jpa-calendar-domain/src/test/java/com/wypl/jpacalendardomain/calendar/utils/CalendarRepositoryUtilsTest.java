package com.wypl.jpacalendardomain.calendar.utils;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.*;
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

import com.wypl.jpacalendardomain.calendar.CalendarFixture;
import com.wypl.jpacalendardomain.calendar.exception.CalendarErrorCode;
import com.wypl.jpacalendardomain.calendar.exception.CalendarException;
import com.wypl.jpacalendardomain.calendar.repository.CalendarRepository;

@ExtendWith(MockitoExtension.class)
class CalendarRepositoryUtilsTest {

	private final CalendarRepository repository;

	public CalendarRepositoryUtilsTest(@Mock CalendarRepository repository) {
		this.repository = repository;
	}

	@DisplayName("Calendar 조회 Util")
	@Nested
	class FindById {

		private long calendarId;

		@BeforeEach
		public void setUp() {
			calendarId = ThreadLocalRandom.current().nextLong();
		}

		@DisplayName("Calendar 조회에 성공한다.")
		@ParameterizedTest
		@EnumSource(CalendarFixture.class)
		void success(CalendarFixture fixture) {
			/* Given */
			given(repository.findById(anyLong()))
				.willReturn(Optional.of(fixture.toCalendar()));

			/* When & Then */
			assertThatCode(() -> CalendarRepositoryUtils.findById(repository, calendarId))
				.doesNotThrowAnyException();
		}

		@DisplayName("Calendar 조회시 존재하지 않으면 예외를 던진다.")
		@Test
		void noSuchCalender() {
			/* When & Then */
			assertThatThrownBy(() -> CalendarRepositoryUtils.findById(repository, calendarId))
				.isInstanceOf(CalendarException.class)
				.hasMessageContaining(CalendarErrorCode.NO_SUCH_CALENDAR.getMessage());
		}
	}
}