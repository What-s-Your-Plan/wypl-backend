package com.wypl.jpascheduledomain.schedule.repository;

import com.wypl.jpascheduledomain.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
