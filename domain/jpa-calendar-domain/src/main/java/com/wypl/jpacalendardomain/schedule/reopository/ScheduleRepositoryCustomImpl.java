package com.wypl.jpacalendardomain.schedule.reopository;

import static com.wypl.jpacalendardomain.schedule.domain.QSchedule.*;

import java.time.LocalDate;
import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wypl.jpacalendardomain.schedule.domain.Schedule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Schedule> findByCalendarIdAndBetweenStartDateAndEndDate(
		final long calendarId,
		final LocalDate startDate,
		final LocalDate endDate
	) {
		return jpaQueryFactory.selectFrom(schedule)
			.where(schedule.scheduleInfo.calendar.id.eq(calendarId)
				.and(schedule.repetition.startDate.loe(endDate)
					.and(schedule.repetition.endDate.goe(startDate))))
			.fetch();
	}
}