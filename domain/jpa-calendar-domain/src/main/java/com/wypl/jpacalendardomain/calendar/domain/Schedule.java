package com.wypl.jpacalendardomain.calendar.domain;

import com.wypl.jpacalendardomain.calendar.data.RepetitionCycle;
import com.wypl.jpacommon.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Entity
@Table(name = "schedule")
public class Schedule extends JpaBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

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

    @Column(name = "repetition_start_date", nullable = false)
    private LocalDate repetitionStartDate;

    @Column(name = "repetition_end_date")
    private LocalDate repetitionEndDate;

    @Enumerated(EnumType.STRING)
    private RepetitionCycle repetitionCycle; // 반복 주기 (일, 주, 달, 년)

    @Column(name = "day_of_week")
    private Integer dayOfWeek; // 반복 요일

    @Column(name = "week_interval")
    private Integer weekInterval; // 주 반복

    // Todo: Review Mapping

    @Builder
    public Schedule(ScheduleInfo scheduleInfo, String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDate repetitionStartDate, LocalDate repetitionEndDate, RepetitionCycle repetitionCycle, Integer dayOfWeek, Integer weekInterval) {
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
}
