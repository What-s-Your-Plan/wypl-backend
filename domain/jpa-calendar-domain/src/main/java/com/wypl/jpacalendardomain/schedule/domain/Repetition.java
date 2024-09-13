package com.wypl.jpacalendardomain.schedule.domain;

import com.wypl.jpacalendardomain.schedule.data.RepetitionCycle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Entity
public class Repetition {
    // Todo : extends BaseEntity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repetition_id")
    private Long repetitionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_info_id")
    @Column(nullable = false)
    private ScheduleInfo scheduleInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "repetition_cycle")
    private RepetitionCycle repetitionCycle;

    @Column(name = "day_of_week")
    private Integer dayOfWeek;      // bit

    @Column(name = "week_of_month")
    private Integer weekOfMonth;

//    @Column(name = "date_of_year")
//    private Object dateOfYear;      // 논의 필요
}
