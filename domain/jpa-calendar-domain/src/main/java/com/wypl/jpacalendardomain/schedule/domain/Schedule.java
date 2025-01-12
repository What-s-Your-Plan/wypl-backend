package com.wypl.jpacalendardomain.schedule.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLRestriction;

import com.wypl.jpacalendardomain.schedule.domain.embedded.Repetition;
import com.wypl.jpacommon.JpaBaseEntity;

import jakarta.persistence.*;
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

	@Column(name = "start_datetime", nullable = false)
	private LocalDateTime startDateTime;

	@Column(name = "end_datetime", nullable = false)
	private LocalDateTime endDateTime;

	@Embedded
	private Repetition repetition;

	// Todo: Review Mapping

	@Builder
	public Schedule(
		ScheduleInfo scheduleInfo,
		String title,
		String description,
		LocalDateTime startDateTime,
		LocalDateTime endDateTime,
		Repetition repetition
	) {
		this.scheduleInfo = scheduleInfo;
		this.title = title;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.repetition = repetition;
	}
}