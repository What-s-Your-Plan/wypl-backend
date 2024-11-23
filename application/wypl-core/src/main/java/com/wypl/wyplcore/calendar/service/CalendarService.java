package com.wypl.wyplcore.calendar.service;

import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpacalendardomain.calendar.domain.MemberCalendar;
import com.wypl.jpacalendardomain.calendar.mapper.ScheduleMapper;
import com.wypl.jpacalendardomain.calendar.repository.ScheduleInfoRepository;
import com.wypl.jpacalendardomain.calendar.repository.ScheduleRepository;
import com.wypl.jpamemberdomain.member.Member;
import com.wypl.wyplcore.auth.domain.AuthMember;
import com.wypl.wyplcore.calendar.data.response.CalendarFindResponse;
import com.wypl.wyplcore.calendar.data.response.FindCalendarResponse;
import com.wypl.wyplcore.calendar.service.strategy.CalendarStrategy;
import com.wypl.wyplcore.schedule.data.CalendarType;
import com.wypl.wyplcore.calendar.data.request.CalendarFindRequest;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final ScheduleRepository scheduleRepository;

    private final Map<CalendarType, CalendarStrategy> calendarStrategyMap;

    /**
     * 회원의 캘린더를 조회한다.
     * @param authMember
     * @param calendarId
     * @param calendarFindRequest
     * @return FindCalendarResponse
     */
    @Transactional
    public FindCalendarResponse findCalendar(AuthMember authMember, long calendarId, CalendarFindRequest calendarFindRequest) {

        Calendar foundCalendar = null;  // FIXME: calendarId로 foundCalendar 엔티티 검증 필요.
        MemberCalendar foundMemberCalendar = null; // FIXME: memberCalendar 엔티티 검증 필요.
        Member foundMember = null; // FIXME: member 엔티티 검증 필요.

        CalendarType calendarType = calendarFindRequest.calendarType();
        LocalDate startDate = calendarFindRequest.startDate();

        CalendarStrategy calendarStrategy = calendarStrategyMap.get(calendarType);
        List<ScheduleFindResponse> foundScheduleFindResponses = calendarStrategy.getAllSchedule(scheduleRepository, foundCalendar.getId(), startDate);

        CalendarFindResponse calendarFindResponse = new CalendarFindResponse(foundCalendar.getId(), foundMemberCalendar.getColor(), foundCalendar.getName());
        return new FindCalendarResponse(calendarFindResponse, foundScheduleFindResponses);
    }
}
