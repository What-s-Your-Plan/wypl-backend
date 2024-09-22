package com.wypl.wyplcore.schedule.service;

import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpascheduledomain.schedule.domain.ScheduleInfo;
import com.wypl.jpascheduledomain.schedule.repository.ScheduleInfoRepository;
import com.wypl.jpascheduledomain.schedule.repository.ScheduleRepository;
import com.wypl.wyplcore.schedule.data.request.ScheduleCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleInfoRepository scheduleInfoRepository;

    public void createSchedule(long memberId, ScheduleCreateRequest scheduleCreateRequest) {

        //Schedule, ScheduleInfo 생성
        Calendar calendar = null;  // FIXME: scheduleInfoRequest의 calendarId로 찾는다.
        ScheduleInfo scheduleInfo = null;

    }
}
