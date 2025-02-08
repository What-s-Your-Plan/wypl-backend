package com.wypl.jpacalendardomain.schedule.reopository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wypl.jpacalendardomain.schedule.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {
}
