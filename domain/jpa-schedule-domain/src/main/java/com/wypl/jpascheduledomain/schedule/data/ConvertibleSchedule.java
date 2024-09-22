package com.wypl.jpascheduledomain.schedule.data;

import java.time.LocalDateTime;

public interface ConvertibleSchedule {

    String getTitle();

    String getDescription();

    LocalDateTime getStartDateTime();

    LocalDateTime getEndDateTime();
}
