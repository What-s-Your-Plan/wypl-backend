package com.wypl.wyplcore.calendar.service.strategy;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.springframework.stereotype.Component;

import com.wypl.wyplcore.calendar.data.DateSearchCondition;
import com.wypl.wyplcore.schedule.data.CalendarType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonthCalendarStrategy implements CalendarStrategy {

	@Override
	public CalendarType getCalendarType() {
		return CalendarType.MONTH;
	}

	@Override
	public DateSearchCondition getDateSearchCondition(LocalDate today) {
		LocalDate searchStartDate = today.withDayOfMonth(1);
		LocalDate searchEndDate = today.with(TemporalAdjusters.lastDayOfMonth());
		return new DateSearchCondition(searchStartDate, searchEndDate);
	}
}
