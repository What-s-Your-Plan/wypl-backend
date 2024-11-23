package com.wypl.wyplcore.schedule.service.repetition;

import java.time.LocalDate;
import java.util.List;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;
import com.wypl.wyplcore.schedule.service.repetition.strategy.RepetitionStrategy;

public class RepetitionService {

	/**
	 * Schedule의 반복 일정에 따라 ScheduleFindResponse를 조회한다.
	 * @param schedule
	 * @param searchStartDate
	 * @param searchEndDate
	 * @return List<ScheduleFindResponse>
	 */
	public static List<ScheduleFindResponse> getScheduleResponses(Schedule schedule, LocalDate searchStartDate, LocalDate searchEndDate) {
		RepetitionStrategy repetitionStrategy = RepetitionStrategyFactory.getRepetitionStrategy(schedule.getRepetitionCycle());
		return repetitionStrategy.getScheduleResponses(schedule, searchStartDate, searchEndDate);
	}
}
