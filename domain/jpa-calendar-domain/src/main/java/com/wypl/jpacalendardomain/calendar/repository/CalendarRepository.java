package com.wypl.jpacalendardomain.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wypl.jpacalendardomain.calendar.domain.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}