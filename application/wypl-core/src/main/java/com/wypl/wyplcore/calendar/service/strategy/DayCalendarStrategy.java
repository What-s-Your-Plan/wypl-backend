package com.wypl.wyplcore.calendar.service.strategy;


import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.jpacalendardomain.calendar.repository.ScheduleRepository;
import com.wypl.wyplcore.schedule.data.CalendarType;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;
import com.wypl.wyplcore.schedule.service.repetition.RepetitionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DayCalendarStrategy implements CalendarStrategy {

    @Override
    public CalendarType getCalendarType() {
        return CalendarType.DAY;
    }

    /**
     * Day 달력의 전체 일정을 조회한다.
     * @param calendarId
     * @param startDate
     * @return List<ScheduleFindResponse>
     */
    @Override
    public List<ScheduleFindResponse> getAllSchedule(ScheduleRepository repository, long calendarId, LocalDate startDate) {

        List<Schedule> schedules = repository.findByCalendarIdAndBetweenStartDateAndEndDate(calendarId, startDate, startDate);

        List<ScheduleFindResponse> scheduleFindResponses = new ArrayList<>();

        for (Schedule schedule : schedules) {
            RepetitionService.getScheduleResponses(schedule, startDate, startDate);
        }
        return scheduleFindResponses;
    }

}
