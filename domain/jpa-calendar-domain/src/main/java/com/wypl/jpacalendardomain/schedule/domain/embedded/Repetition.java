package com.wypl.jpacalendardomain.schedule.domain.embedded;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Repetition {
	@Column(name = "repetition_start_date")
	private LocalDate startDate;

	@Column(name = "repetition_end_date")
	private LocalDate endDate;

	@Getter
	@Enumerated(EnumType.STRING)
	private RepetitionCycle repetitionCycle; // 반복 주기 (일, 주, 달, 년)

	@Column(name = "day_of_week")
	private Integer dayOfWeek; // 반복 요일: Bit Masking

	@Column(name = "week_interval")
	private Integer weekInterval; // 주 반복 (1~3)
}