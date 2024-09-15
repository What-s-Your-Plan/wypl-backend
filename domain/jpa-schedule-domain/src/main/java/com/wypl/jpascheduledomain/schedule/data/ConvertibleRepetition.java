package com.wypl.jpascheduledomain.schedule.data;

public interface ConvertibleRepetition {

    RepetitionCycle getRepetitionCycle();

    Integer getDayOfWeek();

    Integer getWeekInterval();


}
