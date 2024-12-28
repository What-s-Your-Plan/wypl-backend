package com.wypl.jpacalendardomain.calendar.utils;

import com.wypl.common.exception.CallConstructorException;
import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpacalendardomain.calendar.exception.CalendarErrorCode;
import com.wypl.jpacalendardomain.calendar.exception.CalendarException;
import com.wypl.jpacalendardomain.calendar.repository.CalendarRepository;

import lombok.Generated;

public class CalendarRepositoryUtils {

	@Generated
	private CalendarRepositoryUtils() {
		throw new CallConstructorException();
	}

	public static Calendar findById(
		final CalendarRepository repository,
		final long id
	) {
		return repository.findById(id)
			.orElseThrow(() -> new CalendarException(CalendarErrorCode.NO_SUCH_CALENDAR));
	}
}
