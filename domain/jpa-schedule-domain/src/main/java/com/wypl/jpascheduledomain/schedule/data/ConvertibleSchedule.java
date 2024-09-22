package com.wypl.jpascheduledomain.schedule.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ConvertibleSchedule {

    String getTitle();

    String getDescription();

    LocalDateTime getStartDateTime();

    LocalDateTime getEndDateTime();

    LocalDate getRepetitionStartDate();

    LocalDate getRepetitionEndDate();

    RepetitionCycle getRepetitionCycle();

    Integer getDayOfWeek();

    Integer getWeekInterval();
}
