package com.wypl.jpacalendardomain.calendar.domain;

import com.wypl.jpacalendardomain.calendar.data.InviteStatus;
import com.wypl.jpamemberdomain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLRestriction;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Entity
@IdClass(MemberCalendarId.class)
@Table(name = "member_calendar")
public class MemberCalendar {
    // Todo : extends BaseEntity

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @Column(name = "color", length = 6, nullable = false)
    @ColumnDefault("Orange")
    // Todo : Color enum 으로 변경
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private InviteStatus status;
}
