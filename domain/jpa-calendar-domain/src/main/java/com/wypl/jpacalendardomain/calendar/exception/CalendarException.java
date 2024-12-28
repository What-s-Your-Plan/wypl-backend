package com.wypl.jpacalendardomain.calendar.exception;

import com.wypl.common.exception.WyplException;

public class CalendarException extends WyplException {
	public CalendarException(CalendarErrorCode errorCode) {
		super(errorCode);
	}
}
