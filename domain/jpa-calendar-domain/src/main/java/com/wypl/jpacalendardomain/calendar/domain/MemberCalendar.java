package com.wypl.jpacalendardomain.calendar.domain;

import com.wypl.jpacalendardomain.calendar.data.INVITE_STATUS;
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

    @Column(length = 6, nullable = false)
    @ColumnDefault("Orange")
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private INVITE_STATUS status;
}
