package com.wypl.wyplcore.schedule.service;

import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpascheduledomain.schedule.domain.Repetition;
import com.wypl.jpascheduledomain.schedule.domain.ScheduleInfo;
import com.wypl.jpascheduledomain.schedule.mapper.RepetitionMapper;
import com.wypl.jpascheduledomain.schedule.mapper.ScheduleInfoMapper;
import com.wypl.jpascheduledomain.schedule.repository.RepetitionRepository;
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
    private final RepetitionRepository repetitionRepository;

    public void createSchedule(long memberId, ScheduleCreateRequest scheduleCreateRequest) {

        //Schedule, Repetition 생성
        Repetition repetition = RepetitionMapper.toJpaRepetition(scheduleCreateRequest.repetitionRequest());
        Calendar calendar = null;  // FIXME: scheduleInfoRequest의 calendarId로 찾는다.
        ScheduleInfo scheduleInfo = ScheduleInfoMapper.toJpaScheduleInfo(scheduleCreateRequest.scheduleInfoRequest(), calendar);
        scheduleInfo.addRepetition(repetition);
        scheduleInfoRepository.save(scheduleInfo);

        //반복주기


    }
}
