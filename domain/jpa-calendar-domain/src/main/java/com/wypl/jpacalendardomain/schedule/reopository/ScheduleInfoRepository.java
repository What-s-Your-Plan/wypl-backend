package com.wypl.jpacalendardomain.schedule.reopository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wypl.jpacalendardomain.schedule.domain.ScheduleInfo;

public interface ScheduleInfoRepository extends JpaRepository<ScheduleInfo, Long> {
}
