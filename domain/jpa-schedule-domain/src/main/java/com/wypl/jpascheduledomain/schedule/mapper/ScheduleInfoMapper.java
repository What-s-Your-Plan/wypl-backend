package com.wypl.jpascheduledomain.schedule.mapper;


import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpascheduledomain.schedule.data.ConvertibleScheduleInfo;
import com.wypl.jpascheduledomain.schedule.domain.ScheduleInfo;

public class ScheduleInfoMapper {

    public static ScheduleInfo toJpaScheduleInfo(ConvertibleScheduleInfo convertibleScheduleInfo, Calendar calendar) {
        return ScheduleInfo.builder()
                .creatorId(convertibleScheduleInfo.getCreatorId())
                .startDateTime(convertibleScheduleInfo.getStartDateTime())
                .endDateTime(convertibleScheduleInfo.getEndDateTime())
                .calendar(calendar)
                .build();
    }

}
