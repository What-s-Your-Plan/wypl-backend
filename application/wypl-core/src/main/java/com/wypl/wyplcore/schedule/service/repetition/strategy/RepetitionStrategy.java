package com.wypl.wyplcore.schedule.service.repetition.strategy;

import java.time.LocalDate;
import java.util.List;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

public interface RepetitionStrategy {

	List<ScheduleFindResponse> getScheduleResponses(Schedule schedule, LocalDate searchStartDate, LocalDate searchEndDate);

}
