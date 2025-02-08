package com.wypl.jpacalendardomain.schedule.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLRestriction;

import com.wypl.jpacalendardomain.schedule.domain.embedded.Repetition;
import com.wypl.jpacalendardomain.schedule.domain.embedded.RepetitionCycle;
import com.wypl.jpacommon.JpaBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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

	@Embedded
	private Repetition repetition;

	// Todo: Review Mapping

	@Builder
	public Schedule(
		ScheduleInfo scheduleInfo,
		final String title,
		final String description,
		final LocalDateTime startDateTime,
		final LocalDateTime endDateTime,
		final LocalDate repetitionStartDate,
		final LocalDate repetitionEndDate,
		final RepetitionCycle repetitionCycle,
		final Integer dayOfWeek,
		final Integer weekInterval) {
		this.scheduleInfo = scheduleInfo;
		this.title = title;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.repetition = Repetition.builder()
			.startDate(repetitionStartDate)
			.endDate(repetitionEndDate)
			.repetitionCycle(repetitionCycle)
			.dayOfWeek(dayOfWeek)
			.weekInterval(weekInterval)
			.build();
	}

	public boolean isRepetition() {
		return repetition.getRepetitionCycle() != null;
	}

	public boolean existsDayOfWeek() {
		return repetition.getDayOfWeek() != null;
	}

	public Duration getDuration() {
		return Duration.between(this.startDateTime, this.endDateTime);
	}

	public LocalDate getRepetitionStartDate() {
		return repetition.getStartDate();
	}

	public LocalDate getRepetitionEndDate() {
		return repetition.getEndDate();
	}

	public RepetitionCycle getRepetitionCycle() {
		return repetition.getRepetitionCycle();
	}

	public Integer getDayOfWeek() {
		return repetition.getDayOfWeek();
	}

	public Integer getWeekInterval() {
		return repetition.getWeekInterval();
	}
}