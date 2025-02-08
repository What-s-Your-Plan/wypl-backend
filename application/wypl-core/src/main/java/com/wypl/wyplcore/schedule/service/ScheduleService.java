package com.wypl.wyplcore.schedule.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpacalendardomain.schedule.domain.Schedule;
import com.wypl.jpacalendardomain.schedule.domain.ScheduleInfo;
import com.wypl.jpacalendardomain.schedule.mapper.ScheduleInfoMapper;
import com.wypl.jpacalendardomain.schedule.mapper.ScheduleMapper;
import com.wypl.jpacalendardomain.schedule.reopository.ScheduleInfoRepository;
import com.wypl.jpacalendardomain.schedule.reopository.ScheduleRepository;
import com.wypl.jpamemberdomain.member.domain.Member;
import com.wypl.wyplcore.schedule.data.request.ScheduleCreateRequest;
import com.wypl.wyplcore.schedule.data.response.ScheduleInfoCreateResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;
	private final ScheduleInfoRepository scheduleInfoRepository;

	/**
	 * Schedule을 생성한다.
	 * @param authMember
	 * @param scheduleCreateRequest
	 * @return ScheduleInfoCreateResponse
	 */
	@Transactional
	public ScheduleInfoCreateResponse createSchedule(AuthMember authMember,
		ScheduleCreateRequest scheduleCreateRequest) {

		Calendar foundCalendar = null;  // FIXME: scheduleInfoRequest의 calendarId로 찾는다. foundCalendar 엔티티 검증 필요.
		Member foundMember = null; // FIXME: member 엔티티 검증 필요.

		ScheduleInfo scheduleInfo = ScheduleInfoMapper.toJpaScheduleInfo(foundCalendar, authMember.id());
		Schedule schedule = ScheduleMapper.toJpaSchedule(scheduleCreateRequest, scheduleInfo);

		ScheduleInfo savedScheduleInfo = scheduleInfoRepository.save(scheduleInfo);
		Schedule savedSchedule = scheduleRepository.save(schedule);

		return new ScheduleInfoCreateResponse(foundCalendar.getId(), savedScheduleInfo.getId());
	}

}
