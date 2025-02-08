package com.wypl.jpacalendardomain.schedule.repository;

import com.wypl.jpacalendardomain.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wypl.jpacalendardomain.calendar.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {
}
