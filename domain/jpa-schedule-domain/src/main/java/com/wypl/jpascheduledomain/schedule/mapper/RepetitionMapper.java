package com.wypl.jpascheduledomain.schedule.mapper;

import com.wypl.common.exception.CallConstructorException;
import com.wypl.jpascheduledomain.schedule.data.ConvertibleRepetition;
import com.wypl.jpascheduledomain.schedule.domain.Repetition;

public class RepetitionMapper {

    private RepetitionMapper() {
        throw new CallConstructorException();
    }

    public static Repetition toJpaRepetition(ConvertibleRepetition convertibleRepetition) {
        return Repetition.builder()
                .repetitionCycle(convertibleRepetition.getRepetitionCycle())
                .dayOfWeek(convertibleRepetition.getDayOfWeek())
                .weekInterval(convertibleRepetition.getWeekInterval())
                .build();
    }
}
