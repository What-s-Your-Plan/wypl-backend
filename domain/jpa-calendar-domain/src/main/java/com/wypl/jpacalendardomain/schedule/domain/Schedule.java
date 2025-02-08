package com.wypl.jpacalendardomain.schedule.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLRestriction;

import com.wypl.jpacalendardomain.schedule.data.RepetitionCycle;
import com.wypl.jpacommon.JpaBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Entity
@Table(name = "schedule_tbl")
public class Schedule extends JpaBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_info_id", nullable = false)
	private ScheduleInfo scheduleInfo;

	@Column(name = "title", length = 100)
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "start_date_time", nullable = false)
	private LocalDateTime startDateTime;

	@Column(name = "end_date_time", nullable = false)
	private LocalDateTime endDateTime;

	@Column(name = "repetition_start_date")
	private LocalDate repetitionStartDate;

	@Column(name = "repetition_end_date")
	private LocalDate repetitionEndDate;

	@Getter
	@Enumerated(EnumType.STRING)
	private RepetitionCycle repetitionCycle; // 반복 주기 (일, 주, 달, 년)

	@Column(name = "day_of_week")
	private Integer dayOfWeek; // 반복 요일: Bit Masking

	@Column(name = "week_interval")
	private Integer weekInterval; // 주 반복 (1~3)

	// Todo: Review Mapping

	@Builder
	public Schedule(ScheduleInfo scheduleInfo, String title, String description, LocalDateTime startDateTime,
		LocalDateTime endDateTime, LocalDate repetitionStartDate, LocalDate repetitionEndDate,
		RepetitionCycle repetitionCycle, Integer dayOfWeek, Integer weekInterval) {
		this.scheduleInfo = scheduleInfo;
		this.title = title;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.repetitionStartDate = repetitionStartDate;
		this.repetitionEndDate = repetitionEndDate;
		this.repetitionCycle = repetitionCycle;
		this.dayOfWeek = dayOfWeek;
		this.weekInterval = weekInterval;
	}

	public boolean isRepetition() {
		return repetitionCycle != null;
	}

	public boolean existsDayOfWeek() {
		return dayOfWeek != null;
	}

	public Duration getDuration(){
		return Duration.between(this.startDateTime, this.endDateTime);
	}

}
