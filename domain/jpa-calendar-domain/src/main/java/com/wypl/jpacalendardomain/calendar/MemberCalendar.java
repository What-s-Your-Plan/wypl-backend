package com.wypl.jpacalendardomain.calendar;

import com.wypl.jpamemberdomain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// IdClass 달아야 됨
public class MemberCalendar {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;
    private String color;
    @Enumerated(EnumType.STRING)
    private INVITE_STATUS status;
}
