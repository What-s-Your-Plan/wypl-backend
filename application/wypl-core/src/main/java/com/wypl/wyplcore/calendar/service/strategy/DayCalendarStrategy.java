package com.wypl.wyplcore.calendar.service.strategy;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.wypl.wyplcore.calendar.data.DateSearchCondition;
import com.wypl.wyplcore.schedule.data.CalendarType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DayCalendarStrategy implements CalendarStrategy {

	@Override
	public CalendarType getCalendarType() {
		return CalendarType.DAY;
	}

	@Override
	public DateSearchCondition getDateSearchCondition(LocalDate today) {
		return new DateSearchCondition(today, today);
	}

}
