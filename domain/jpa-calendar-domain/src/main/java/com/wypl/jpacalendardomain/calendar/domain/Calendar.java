package com.wypl.jpacalendardomain.calendar.domain;

import com.wypl.jpacommon.JpaBaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Entity
@Table(name = "calendar")
public class Calendar extends JpaBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "calendar_id")
	private Long calendarId;

	@Column(name = "name", length = 20, nullable = false)
	private String name;

	@Column(name = "description", length = 50)
	private String description;

	@Column(name = "owner_id")
	private Long ownerId;

	@OneToMany
	private List<ScheduleInfo> scheduleInfos;

	// Todo : boolean type 설정
	//    private Boolean isShared;
}
