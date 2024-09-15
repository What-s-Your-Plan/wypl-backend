package com.wypl.jpascheduledomain.schedule.repository;

import com.wypl.jpascheduledomain.schedule.domain.Repetition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepetitionRepository extends JpaRepository<Repetition, Long> {
}
