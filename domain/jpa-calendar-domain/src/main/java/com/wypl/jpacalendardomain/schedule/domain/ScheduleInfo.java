package com.wypl.jpacalendardomain.schedule.domain;

import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Entity
public class ScheduleInfo {
    // Todo : extends BaseEntity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_info_id")
    private Long scheduleInfoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id")
    @Column(nullable = false)
    private Calendar calendar;

    @Column(name = "start_datetime", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_datetime", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "creator_id")
    private Long creatorId;

}
