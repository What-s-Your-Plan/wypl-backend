package com.wypl.wyplcore.calendar.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wypl.applicationcommon.WyplResponseEntity;
import com.wypl.googleoauthclient.annotation.Authenticated;
import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.wyplcore.calendar.data.response.CalendarSchedulesResponse;
import com.wypl.wyplcore.calendar.service.CalendarService;
import com.wypl.wyplcore.schedule.data.CalendarType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {
	private final CalendarService calendarService;

	@GetMapping
	public WyplResponseEntity getCalendar(
		@Authenticated AuthMember authMember,
		@PathVariable("calendarId") int calendarId,
		@RequestParam("type") CalendarType calendarType,
		@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate today
		) {
		CalendarSchedulesResponse response = calendarService.findCalendar(authMember, calendarId, calendarType, today);
		return WyplResponseEntity.ok(response, "달력 조회에 성공했습니다.");
	}

}
