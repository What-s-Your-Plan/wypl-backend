package com.wypl.wyplcore.calendar.service;

import java.time.LocalDate;

public class CalendarServiceUtil {

	public static LocalDate getMaxDate(LocalDate date1, LocalDate date2) {
		return date1.isBefore(date2) ? date2 : date1;
	}

	public static LocalDate getMinDate(LocalDate date1, LocalDate date2) {
		return date1.isBefore(date2) ? date1 : date2;
	}

}
