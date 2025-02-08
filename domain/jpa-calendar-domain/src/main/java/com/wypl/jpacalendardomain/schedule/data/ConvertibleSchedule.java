package com.wypl.jpacalendardomain.schedule.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.wypl.jpacalendardomain.schedule.domain.embedded.RepetitionCycle;

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