package com.wypl.jpacalendardomain.schedule.repository;

import com.wypl.jpacalendardomain.schedule.domain.ScheduleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wypl.jpacalendardomain.calendar.domain.ScheduleInfo;

public interface ScheduleInfoRepository extends JpaRepository<ScheduleInfo, Long> {
}
