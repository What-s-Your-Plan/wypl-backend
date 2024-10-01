package com.wypl.wyplcore.schedule.controller;

import com.wypl.common.Message;
import com.wypl.wyplcore.auth.annotation.Authenticated;
import com.wypl.wyplcore.auth.domain.AuthMember;
import com.wypl.wyplcore.schedule.data.request.ScheduleCreateRequest;
import com.wypl.wyplcore.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addSchedule(@Authenticated AuthMember authMember, @RequestBody ScheduleCreateRequest scheduleCreateRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED).body(
                        Message.withBody("일정 생성 성공",
                                scheduleService.createSchedule(authMember.id(), scheduleCreateRequest))
                );

    }
}
