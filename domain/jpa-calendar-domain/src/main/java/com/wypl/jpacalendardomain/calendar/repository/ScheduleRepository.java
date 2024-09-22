package com.wypl.jpacalendardomain.calendar.repository;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
