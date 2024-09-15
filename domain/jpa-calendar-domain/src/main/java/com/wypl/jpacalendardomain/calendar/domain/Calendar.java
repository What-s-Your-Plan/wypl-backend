package com.wypl.jpacalendardomain.calendar.domain;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "calendar")
public class Calendar {
	// Todo : extends BaseEntity
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

	// Todo : boolean type 설정
	//    private Boolean isShared;
}
