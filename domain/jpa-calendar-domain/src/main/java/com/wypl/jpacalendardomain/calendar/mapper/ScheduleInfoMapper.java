package com.wypl.jpacalendardomain.calendar.mapper;


import com.wypl.jpacalendardomain.calendar.data.ConvertibleScheduleInfo;
import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpacalendardomain.calendar.domain.ScheduleInfo;

public class ScheduleInfoMapper {

    public static ScheduleInfo toJpaScheduleInfo(ConvertibleScheduleInfo convertibleScheduleInfo, Calendar calendar) {
        return ScheduleInfo.builder()
                .creatorId(convertibleScheduleInfo.getCreatorId())
                .calendar(calendar)
                .build();
    }

}
