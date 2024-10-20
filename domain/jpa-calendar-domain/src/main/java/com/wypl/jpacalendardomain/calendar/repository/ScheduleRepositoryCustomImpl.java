package com.wypl.jpacalendardomain.calendar.repository;

import java.time.LocalDate;
import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wypl.jpacalendardomain.calendar.domain.QSchedule;
import com.wypl.jpacalendardomain.calendar.domain.QScheduleInfo;
import com.wypl.jpacalendardomain.calendar.domain.Schedule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;
	private final QSchedule schedule = QSchedule.schedule;

	@Override
	public List<Schedule> findByCalendarIdAndBetweenStartDateAndEndDate(long calendarId, LocalDate startDate, LocalDate endDate) {
		return jpaQueryFactory.selectFrom(schedule)
				.where(schedule.scheduleInfo.calendar.id.eq(calendarId)
						.and(schedule.repetitionStartDate.loe(endDate)
							.and(schedule.repetitionEndDate.goe(startDate))))
					.fetch();
	}
}
