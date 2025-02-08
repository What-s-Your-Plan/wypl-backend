package com.wypl.jpacalendardomain.calendar.domain;


import com.wypl.jpacalendardomain.schedule.domain.ScheduleInfo;
import com.wypl.jpacommon.JpaBaseEntity;
import jakarta.persistence.*;
import java.util.List;
import org.hibernate.annotations.SQLRestriction;

import com.wypl.jpacommon.JpaBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Entity
@Table(name = "calendar_tbl")
public class Calendar extends JpaBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "calendar_id")
	private Long id;

	@Column(name = "name", length = 20, nullable = false)
	private String name;

	@Column(name = "description", length = 50)
	private String description;

	@Column(name = "owner_id")
	private Long ownerId;

	@OneToMany(mappedBy = "calendar")
	private List<ScheduleInfo> scheduleInfos;

	@OneToMany(mappedBy = "calendar")
	private List<MemberCalendar> memberCalendars;

	@Column(name = "is_shared")
	private Boolean isShared;

	@Builder
	public Calendar(String name, String description, Long ownerId, List<ScheduleInfo> scheduleInfos, Boolean isShared) {
		this.name = name;
		this.description = description;
		this.ownerId = ownerId;
		this.scheduleInfos = scheduleInfos;
		this.isShared = isShared;
	}
}
