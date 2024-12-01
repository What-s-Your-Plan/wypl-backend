package com.wypl.wyplcore.calendar.service.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wypl.wyplcore.schedule.data.CalendarType;

@Configuration
public class CalendarStrategyConfig {

	private final Map<CalendarType, CalendarStrategy> calendarStrategyMap = new HashMap<>();

	@Autowired
	public CalendarStrategyConfig(List<CalendarStrategy> calendarStrategies) {
		calendarStrategies.forEach(calendarStrategy -> {
			calendarStrategyMap.put(calendarStrategy.getCalendarType(), calendarStrategy);
		});
	}

	@Bean
	public Map<CalendarType, CalendarStrategy> calendarStrategyMap() {
		return calendarStrategyMap;
	}
}
