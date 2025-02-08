package com.wypl.common.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
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
	 * standardDate 이거나 그 이후의 month 와 dayOfMonth 를 만족하는 날짜를 찾는다.
	 * @param standardDate : 기준이 되는 날짜
	 * @param month : 탐색하려는 Month
	 * @param dayOfMonth : 탐색하려는 dayOfMonth
	 * @return 날짜
	 */
	public static LocalDate findNextOrSame(LocalDate standardDate, Month month, int dayOfMonth) {
		if (isAfter(standardDate.getMonth(), standardDate.getDayOfMonth(), month, dayOfMonth)) {
			return LocalDate.of(standardDate.getYear() + 1, month, dayOfMonth);
		}
		return LocalDate.of(standardDate.getYear(), month, dayOfMonth);
	}

	private static boolean isAfter(Month firstMonth, int firstDayOfMonth, Month secondMonth, int secondDayOfMonth) {
		if (firstMonth.getValue() > secondMonth.getValue()) {
			return true;
		}
		if (firstMonth.getValue() == secondMonth.getValue()) {
			return firstDayOfMonth > secondDayOfMonth;
		}
		return false;
	}
}
