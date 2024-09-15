package com.wypl.jpacalendardomain.calendar.domain;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class MemberCalendarId implements Serializable {
	private Long member;
	private Long calendar;
}
