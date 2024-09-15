package com.wypl.jpascheduledomain.schedule.repository;

import com.wypl.jpascheduledomain.schedule.domain.ScheduleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleInfoRepository extends JpaRepository<ScheduleInfo, Long> {
}
