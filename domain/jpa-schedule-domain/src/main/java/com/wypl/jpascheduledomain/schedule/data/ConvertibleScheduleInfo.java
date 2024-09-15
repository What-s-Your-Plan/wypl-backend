package com.wypl.jpascheduledomain.schedule.data;

import java.time.LocalDateTime;

public interface ConvertibleScheduleInfo {

    Long getCreatorId();

    LocalDateTime getStartDateTime();

    LocalDateTime getEndDateTime();

}
