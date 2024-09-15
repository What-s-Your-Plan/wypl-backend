package com.wypl.jpacalendardomain.calendar.domain;

import org.hibernate.annotations.SQLRestriction;

import com.wypl.common.Color;
import com.wypl.jpacalendardomain.calendar.data.InviteStatus;
import com.wypl.jpamemberdomain.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@Column(name = "color", length = 6)
	private Color color;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 10, nullable = false)
	private InviteStatus status;
}
