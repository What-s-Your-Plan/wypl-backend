package com.wypl.wyplcore.schedule.service.repetition;

import java.time.LocalDate;
import java.util.List;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;
import com.wypl.wyplcore.schedule.service.repetition.strategy.RepetitionStrategy;

public class RepetitionService {

	public static List<ScheduleFindResponse> getScheduleResponses(Schedule schedule, LocalDate searchStartDate, LocalDate searchEndDate) {
		RepetitionStrategy repetitionStrategy = RepetitionStrategyFactory.getRepetitionStrategy(schedule.getRepetitionCycle());
		return repetitionStrategy.getScheduleResponses(schedule, searchStartDate, searchEndDate);
	}
}
