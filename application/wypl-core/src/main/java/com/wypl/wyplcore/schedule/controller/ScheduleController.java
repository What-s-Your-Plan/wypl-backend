package com.wypl.wyplcore.schedule.controller;

import com.wypl.applicationcommon.WyplResponseEntity;
import com.wypl.wyplcore.auth.annotation.Authenticated;
import com.wypl.wyplcore.auth.domain.AuthMember;
import com.wypl.wyplcore.schedule.data.request.ScheduleCreateRequest;
import com.wypl.wyplcore.schedule.data.response.ScheduleInfoCreateResponse;
import com.wypl.wyplcore.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule/v2/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public WyplResponseEntity<ScheduleInfoCreateResponse> addSchedule(@Authenticated AuthMember authMember, @RequestBody ScheduleCreateRequest scheduleCreateRequest) {
        ScheduleInfoCreateResponse response = scheduleService.createSchedule(authMember, scheduleCreateRequest);
        return WyplResponseEntity.created(response, "일정이 생성됐습니다.");
    }
}
