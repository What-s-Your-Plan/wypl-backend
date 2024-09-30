package com.wypl.jpacalendardomain.calendar.domain;

import com.wypl.jpacommon.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Entity
@Table(name = "schedule_info_tbl")
public class ScheduleInfo extends JpaBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;

    @Column(name = "creator_id")
    private Long creatorId;

    @OneToMany(mappedBy = "scheduleInfo")
    private List<Schedule> schedules;

    @Builder
    public ScheduleInfo(Calendar calendar, Long creatorId) {
        this.calendar = calendar;
        this.creatorId = creatorId;
    }
}
