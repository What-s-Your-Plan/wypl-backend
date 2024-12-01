package com.wypl.jpacalendardomain.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wypl.jpacalendardomain.calendar.domain.ScheduleInfo;

public interface ScheduleInfoRepository extends JpaRepository<ScheduleInfo, Long> {
}
