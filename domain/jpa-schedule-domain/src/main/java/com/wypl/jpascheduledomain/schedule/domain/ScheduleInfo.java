package com.wypl.jpascheduledomain.schedule.domain;

import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpacommon.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Entity
@Table(name = "schedule_info")
public class ScheduleInfo extends JpaBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_info_id")
    private Long scheduleInfoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;

    @Column(name = "start_datetime", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_datetime", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "creator_id")
    private Long creatorId;

    @OneToMany(mappedBy = "scheduleInfo")
    private List<Repetition> repetitions;

    @Builder
    public ScheduleInfo(Calendar calendar, LocalDateTime startDateTime, LocalDateTime endDateTime, Long creatorId) {
        this.calendar = calendar;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.creatorId = creatorId;
    }

    public void addRepetition(Repetition repetition) {
        repetitions.add(repetition);
        repetition.setScheduleInfo(this);
    }

    public void removeRepetition(Repetition repetition) {
        repetitions.remove(repetition);
        repetition.setScheduleInfo(null);
    }
}
