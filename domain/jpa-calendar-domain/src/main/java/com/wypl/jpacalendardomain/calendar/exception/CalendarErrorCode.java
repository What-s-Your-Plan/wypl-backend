package com.wypl.jpacalendardomain.calendar.exception;

import com.wypl.common.exception.ServerErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CalendarErrorCode implements ServerErrorCode {
	NO_SUCH_CALENDAR(400, "CALENDAR_001", "존재하지 않는 달력입니다.");

	private final int statusCode;
	private final String errorCode;
	private final String message;
}
