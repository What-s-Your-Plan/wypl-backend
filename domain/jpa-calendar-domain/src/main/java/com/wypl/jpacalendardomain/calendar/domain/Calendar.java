package com.wypl.jpacalendardomain.calendar.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Entity
public class Calendar {
    // Todo : extends BaseEntity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    private Long calendarId;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 50)
    private String description;

    @Column(nullable = false)
    private Long ownerId;

    // Todo : boolean type 설정
//    private Boolean isShared;
}
