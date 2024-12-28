package com.wypl.common.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {

	/**
	 * 기준 날짜와 같거나 이후의 dayOfYear를 갖는 날짜를 찾는다.
	 * @param dayOfYear from 1 to 366
	 * @param targetDate 기준 날짜
	 * @return LocalDate, not null
	 */
	public static LocalDate getNextOrSame(int dayOfYear, LocalDate targetDate) {
		if (dayOfYear < targetDate.getDayOfYear()) {
			return targetDate.plusYears(1).withDayOfYear(dayOfYear);
		}
		return targetDate.withDayOfYear(dayOfYear);
	}

	/**
	 * 기준 날짜와 같거나 이후의 가장 가까운 dayOfWeek에 해당하는 날짜를 찾는다.
	 * @param dayOfWeek 요일, from 1 to 7
	 * @param targetDate 기준 날짜
	 * @return LocalDate, not null
	 */
	public static LocalDate getNextOrSame(DayOfWeek dayOfWeek, LocalDate targetDate) {
		return targetDate.with(TemporalAdjusters.nextOrSame(dayOfWeek));
	}
}
