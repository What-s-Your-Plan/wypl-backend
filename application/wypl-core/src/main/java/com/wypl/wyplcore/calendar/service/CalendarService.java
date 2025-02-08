package com.wypl.wyplcore.calendar.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpacalendardomain.calendar.domain.MemberCalendar;
import com.wypl.jpacalendardomain.schedule.domain.Schedule;
import com.wypl.jpacalendardomain.schedule.reopository.ScheduleRepository;
import com.wypl.jpamemberdomain.member.domain.Member;
import com.wypl.wyplcore.calendar.data.DateSearchCondition;
import com.wypl.wyplcore.calendar.data.request.CalendarFindRequest;
import com.wypl.wyplcore.calendar.data.response.CalendarSchedulesResponse;
import com.wypl.wyplcore.calendar.service.strategy.CalendarStrategy;
import com.wypl.wyplcore.schedule.data.CalendarType;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;
import com.wypl.wyplcore.schedule.service.repetition.RepetitionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarService {

	private final ScheduleRepository scheduleRepository;

	private final Map<CalendarType, CalendarStrategy> calendarStrategyMap;

	/**
	 * 캘린더의 일정을 조회한다.
	 * @param authMember : 인증된 사용자 정보
	 * @param calendarId : 조회할 캘린더 ID
	 * @param calendarFindRequest : 캘린더 조회 조건
	 * @return FindCalendarResponse
	 */
	@Transactional(readOnly = true)
	public CalendarSchedulesResponse findCalendar(AuthMember authMember, long calendarId,
		CalendarFindRequest calendarFindRequest) {

		Calendar foundCalendar = null;  // FIXME: calendarId로 foundCalendar 엔티티 검증 필요.
		MemberCalendar foundMemberCalendar = null; // FIXME: memberCalendar 엔티티 검증 필요.
		Member foundMember = null; // FIXME: member 엔티티 검증 필요.

		DateSearchCondition dateSearchCondition = getDateSearchCondition(calendarFindRequest.today(),
			calendarFindRequest.calendarType());

		List<Schedule> schedules = scheduleRepository.findByCalendarIdAndBetweenStartDateAndEndDate(calendarId,
			dateSearchCondition.startDate(), dateSearchCondition.endDate());

		List<ScheduleFindResponse> responses = schedules.stream()
			.flatMap(schedule -> getScheduleResponsesWithRepetition(schedule, dateSearchCondition.startDate(),
				dateSearchCondition.endDate()).stream())
			.toList();

		return new CalendarSchedulesResponse(responses.size(), responses);
	}

	/**
	 * RepetitionService를 통해 반복 일정을 가공하여 조회한다.
	 * @param schedule : 일정 정보
	 * @param startDate : 조회 시작일
	 * @param endDate : 조회 종료일
	 * @return List<ScheduleFindResponse> : 일정 반복 정보를 통해 리스트 형태로 가공하여 반환된다.
	 */
	private List<ScheduleFindResponse> getScheduleResponsesWithRepetition(Schedule schedule, LocalDate startDate,
		LocalDate endDate) {
		return RepetitionService.getScheduleResponses(schedule, startDate, endDate);
	}

	/**
	 * CalendarType에 따라 DateSearchCondition을 반환한다.
	 * @param today : 조회 기준일
	 * @param calendarType : 조회할 캘린더 타입
	 * @return DateSearchCondition : DateSearchCondition 객체를 반환한다.
	 */
	private DateSearchCondition getDateSearchCondition(LocalDate today, CalendarType calendarType) {
		return calendarStrategyMap.get(calendarType).getDateSearchCondition(today);
	}
}
