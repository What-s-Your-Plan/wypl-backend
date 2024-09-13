package com.wypl.jpacalendardomain.calendar.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class MemberCalendarId implements Serializable {
    private Long member;
    private Long calendar;
}
