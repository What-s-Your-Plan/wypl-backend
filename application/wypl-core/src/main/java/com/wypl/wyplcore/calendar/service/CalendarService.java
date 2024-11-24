package com.wypl.wyplcore.calendar.service;

import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpacalendardomain.calendar.domain.MemberCalendar;
import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.jpacalendardomain.calendar.repository.ScheduleRepository;
import com.wypl.jpamemberdomain.member.domain.Member;
import com.wypl.wyplcore.calendar.data.DateSearchCondition;
import com.wypl.wyplcore.calendar.data.response.CalendarSchedulesResponse;
import com.wypl.wyplcore.calendar.service.strategy.CalendarStrategy;
import com.wypl.wyplcore.schedule.data.CalendarType;
import com.wypl.wyplcore.calendar.data.request.CalendarFindRequest;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;
import com.wypl.wyplcore.schedule.service.repetition.RepetitionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final ScheduleRepository scheduleRepository;

    private final Map<CalendarType, CalendarStrategy> calendarStrategyMap;

    /**
     * 캘린더 정보와 캘린더의 일정을 함께 조회한다.
     * @param authMember
     * @param calendarId
     * @param calendarFindRequest
     * @return FindCalendarResponse
     */
    @Transactional
    public CalendarSchedulesResponse findCalendar(AuthMember authMember, long calendarId, CalendarFindRequest calendarFindRequest) {

        Calendar foundCalendar = null;  // FIXME: calendarId로 foundCalendar 엔티티 검증 필요.
        MemberCalendar foundMemberCalendar = null; // FIXME: memberCalendar 엔티티 검증 필요.
        Member foundMember = null; // FIXME: member 엔티티 검증 필요.

        DateSearchCondition dateSearchCondition = getDateSearchCondition(calendarFindRequest.today(), calendarFindRequest.calendarType());

        List<Schedule> schedules = scheduleRepository.findByCalendarIdAndBetweenStartDateAndEndDate(calendarId, dateSearchCondition.startDate(), dateSearchCondition.endDate());

        List<ScheduleFindResponse> scheduleFindResponses = new ArrayList<>();
        schedules.forEach(schedule -> {
            scheduleFindResponses.addAll(getScheduleResponsesWithRepetition(schedule, dateSearchCondition.startDate(), dateSearchCondition.endDate()));
        });

        return new CalendarSchedulesResponse(scheduleFindResponses.size(), scheduleFindResponses);
    }

    /**
     * RepetitionService를 통해 반복 일정을 가공하여 조회한다.
     * @param schedule
     * @param startDate
     * @param endDate
     * @return List<ScheduleFindResponse> : 일정 반복 정보를 통해 리스트 형태로 가공하여 반환된다.
     */
    private List<ScheduleFindResponse> getScheduleResponsesWithRepetition(Schedule schedule, LocalDate startDate, LocalDate endDate) {
        return RepetitionService.getScheduleResponses(schedule, startDate, endDate);
    }

    /**
     * CalendarType에 따라 DateSearchCondition을 반환한다.
     * @param today
     * @param calendarType
     * @return DateSearchCondition : startDate와 endDate를 가지고 있는 DateSearchCondition 객체를 반환한다.
     */
    private DateSearchCondition getDateSearchCondition(LocalDate today, CalendarType calendarType) {
        return calendarStrategyMap.get(calendarType).getDateSearchCondition(today);
    }
}
