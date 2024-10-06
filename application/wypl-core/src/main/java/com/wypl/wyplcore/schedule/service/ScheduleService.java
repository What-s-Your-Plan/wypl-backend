package com.wypl.wyplcore.schedule.service;

import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.jpacalendardomain.calendar.domain.ScheduleInfo;
import com.wypl.jpacalendardomain.calendar.mapper.ScheduleInfoMapper;
import com.wypl.jpacalendardomain.calendar.mapper.ScheduleMapper;
import com.wypl.jpacalendardomain.calendar.repository.ScheduleInfoRepository;
import com.wypl.jpacalendardomain.calendar.repository.ScheduleRepository;
import com.wypl.jpamemberdomain.member.Member;
import com.wypl.wyplcore.auth.domain.AuthMember;
import com.wypl.wyplcore.schedule.data.request.ScheduleCreateRequest;
import com.wypl.wyplcore.schedule.data.response.ScheduleInfoCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleInfoRepository scheduleInfoRepository;

    @Transactional
    public ScheduleInfoCreateResponse createSchedule(AuthMember authMember, ScheduleCreateRequest scheduleCreateRequest) {

        Calendar foundCalendar = null;  // FIXME: scheduleInfoRequest의 calendarId로 찾는다. foundCalendar 엔티티 검증 필요.
        Member foundMember = null; // FIXME: member 엔티티 검증 필요.

        ScheduleInfo scheduleInfo = ScheduleInfoMapper.toJpaScheduleInfo(foundCalendar, authMember);
        Schedule schedule = ScheduleMapper.toJpaSchedule(scheduleCreateRequest, scheduleInfo);

        ScheduleInfo savedScheduleInfo = scheduleInfoRepository.save(scheduleInfo);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleInfoCreateResponse(foundCalendar.getId(), savedScheduleInfo.getId());
    }

}
