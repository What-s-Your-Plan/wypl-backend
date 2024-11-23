package com.wypl.wyplcore.calendar.service.strategy;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.jpacalendardomain.calendar.repository.ScheduleRepository;
import com.wypl.wyplcore.schedule.data.CalendarType;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;
import com.wypl.wyplcore.schedule.service.repetition.RepetitionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WeekCalendarStrategy implements CalendarStrategy {

    @Override
    public CalendarType getCalendarType() {
        return CalendarType.WEEK;
    }

    /**
     * Week 달력의 전체 일정 조회한다.
     * @param calendarId
     * @param startDate
     * @return List<ScheduleFindResponse>
     */
    @Override
    public List<ScheduleFindResponse> getAllSchedule(ScheduleRepository repository, long calendarId, LocalDate startDate) {

        LocalDate searchStartDate = startDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate searchEndDate = startDate.plusDays(6);

        List<Schedule> schedules = repository.findByCalendarIdAndBetweenStartDateAndEndDate(calendarId, searchStartDate, searchEndDate);

        List<ScheduleFindResponse> scheduleFindResponses = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleFindResponses.addAll(RepetitionService.getScheduleResponses(schedule, searchStartDate, searchEndDate));
        }
        return scheduleFindResponses;
    }
}
