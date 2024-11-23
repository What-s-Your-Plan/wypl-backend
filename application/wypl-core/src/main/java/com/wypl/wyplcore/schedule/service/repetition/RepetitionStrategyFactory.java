package com.wypl.wyplcore.schedule.service.repetition;

import java.util.HashMap;
import java.util.Map;

import com.wypl.jpacalendardomain.calendar.data.RepetitionCycle;
import com.wypl.wyplcore.schedule.service.repetition.strategy.DayRepetitionStrategy;
import com.wypl.wyplcore.schedule.service.repetition.strategy.MonthRepetitionStrategy;
import com.wypl.wyplcore.schedule.service.repetition.strategy.RepetitionStrategy;
import com.wypl.wyplcore.schedule.service.repetition.strategy.WeekRepetitionStrategy;
import com.wypl.wyplcore.schedule.service.repetition.strategy.YearRepetitionStrategy;

public class RepetitionStrategyFactory {

	private static RepetitionStrategyFactory instance;

	private final Map<RepetitionCycle, RepetitionStrategy> map = new HashMap<>();

	private RepetitionStrategyFactory() {
		map.put(RepetitionCycle.DAY, new DayRepetitionStrategy());
		map.put(RepetitionCycle.WEEK, new WeekRepetitionStrategy());
		map.put(RepetitionCycle.MONTH, new MonthRepetitionStrategy());
		map.put(RepetitionCycle.YEAR, new YearRepetitionStrategy());
	}

	/**
	 * RepetitionCycle에 따른 RepetitionStrategy를 반환한다.
	 * @param repetitionCycle
	 * @return RepetitionStrategy
	 */
	public static RepetitionStrategy getRepetitionStrategy(RepetitionCycle repetitionCycle) {
		if (instance == null) {
			instance = new RepetitionStrategyFactory();
		}
		return instance.map.get(repetitionCycle);
	}
}
