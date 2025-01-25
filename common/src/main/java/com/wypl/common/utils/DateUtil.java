package com.wypl.common.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {

	/**
	 * 기준 날짜와 같거나 이후의 가장 가까운 dayOfWeek에 해당하는 날짜를 찾는다.
	 * @param targetDate 기준 날짜
	 * @param dayOfWeek 요일, from 1 to 7
	 * @return LocalDate, not null
	 */
	public static LocalDate findNextOrSameByDayOfWeek(LocalDate targetDate, DayOfWeek dayOfWeek) {
		return targetDate.with(TemporalAdjusters.nextOrSame(dayOfWeek));
	}

	/**
	 * 기준 날짜와 같거나 이후의 dayOfMonth를 갖는 날짜를 찾는다.
	 * @param targetDate 기준 날짜
	 * @param dayOfMonth from 1 to 31
	 * @return LocalDate, not null
	 */
	public static LocalDate findNextOrSameByDayOfMonth(LocalDate targetDate, int dayOfMonth) {
		if (dayOfMonth < targetDate.getDayOfMonth()) {
			return targetDate.plusMonths(1).withDayOfMonth(dayOfMonth);
		}
		return targetDate.withDayOfMonth(dayOfMonth);
	}

	// Todo: 2/30 인데, 다음년도에는 2.29 까지밖에 없다면?
	/**
	 *  dayOfYear 를 기준으로, targetDate 와 같거나 그 이후에 해당하는 날짜를 찾는다.
	 * @param targetDate 기준 날짜
	 * @param dayOfYear from 1 to 366
	 * @return LocalDate, not null
	 */
	public static LocalDate findNextOrSame(LocalDate targetDate, int dayOfYear) {
		if (dayOfYear < targetDate.getDayOfYear()) {
			return targetDate.plusYears(1).withDayOfYear(dayOfYear);
		}
		return targetDate.withDayOfYear(dayOfYear);
	}
}
