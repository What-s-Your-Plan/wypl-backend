package com.wypl.jpascheduledomain.schedule.domain;

import com.wypl.jpacommon.JpaBaseEntity;
import com.wypl.jpascheduledomain.schedule.data.RepetitionCycle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Entity
@Table(name = "repetition")
public class Repetition extends JpaBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repetition_id")
    private Long repetitionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_info_id", nullable = false)
    private ScheduleInfo scheduleInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "repetition_cycle")
    private RepetitionCycle repetitionCycle;

    @Column(name = "day_of_week")
    private Integer dayOfWeek;      // 주마다 반복할 요일

    @Column(name = "week_interval")
    private Integer weekInterval; // 반복 주기를 나타내는 변수 (예: 2주마다)

    @Builder
    public Repetition(ScheduleInfo scheduleInfo, RepetitionCycle repetitionCycle, Integer dayOfWeek, Integer weekInterval) {
        this.scheduleInfo = scheduleInfo;
        this.repetitionCycle = repetitionCycle;
        this.dayOfWeek = dayOfWeek;
        this.weekInterval = weekInterval;
    }
}
