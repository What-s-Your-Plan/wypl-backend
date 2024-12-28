package com.wypl.wyplcore.schedule.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.jpacalendardomain.calendar.domain.ScheduleInfo;
import com.wypl.jpacalendardomain.calendar.mapper.ScheduleInfoMapper;
import com.wypl.jpacalendardomain.calendar.mapper.ScheduleMapper;
import com.wypl.jpacalendardomain.calendar.repository.CalendarRepository;
import com.wypl.jpacalendardomain.calendar.repository.ScheduleInfoRepository;
import com.wypl.jpacalendardomain.calendar.repository.ScheduleRepository;
import com.wypl.jpacalendardomain.calendar.utils.CalendarRepositoryUtils;
import com.wypl.jpamemberdomain.member.domain.Member;
import com.wypl.jpamemberdomain.member.repository.MemberRepository;
import com.wypl.jpamemberdomain.member.utils.MemberRepositoryUtils;
import com.wypl.wyplcore.schedule.data.request.ScheduleCreateRequest;
import com.wypl.wyplcore.schedule.data.response.ScheduleInfoCreateResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
	private final ScheduleRepository scheduleRepository;
	private final ScheduleInfoRepository scheduleInfoRepository;

	private final MemberRepository memberRepository;
	private final CalendarRepository calendarRepository;

	@Transactional
	public ScheduleInfoCreateResponse save(
		final AuthMember authMember,
		final ScheduleCreateRequest scheduleCreateRequest
	) {
		Calendar foundCalendar =
			CalendarRepositoryUtils.findById(calendarRepository, scheduleCreateRequest.calenderId());
		Member foundMember = MemberRepositoryUtils.findById(memberRepository, authMember.id());

		ScheduleInfo scheduleInfo = ScheduleInfoMapper.toJpaScheduleInfo(foundCalendar, foundMember.getMemberId());
		ScheduleInfo savedScheduleInfo = scheduleInfoRepository.save(scheduleInfo);

		Schedule schedule = ScheduleMapper.toJpaSchedule(scheduleCreateRequest, savedScheduleInfo);
		Schedule savedSchedule = scheduleRepository.save(schedule);

		return ScheduleInfoCreateResponse.from(savedSchedule);
	}
}
