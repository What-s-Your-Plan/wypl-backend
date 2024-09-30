package com.wypl.jpacalendardomain.calendar.mapper;

import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpacalendardomain.calendar.domain.ScheduleInfo;

public class ScheduleInfoMapper {

    public static ScheduleInfo toJpaScheduleInfo(Calendar calendar, long creatorId) {
        return ScheduleInfo.builder()
                .creatorId(creatorId)
                .calendar(calendar)
                .build();
    }
}
