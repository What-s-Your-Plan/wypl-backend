package com.wypl.jpamemberdomain.member.domain;

import java.time.LocalDate;

import com.wypl.common.Color;
import com.wypl.jpacommon.JpaBaseEntity;
import com.wypl.jpamemberdomain.member.TimeZone;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "member_tbl")
public class Member extends JpaBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "email", length = 50, unique = true, nullable = false)
	private String email;

	@Column(name = "nickname", length = 20, nullable = false)
	private String nickname;

	@Column(name = "birthday")
	private LocalDate birthday;

	@Column(name = "profile_image", length = 100)
	private String profileImage;

	@Enumerated(EnumType.STRING)
	@Column(name = "color", length = 20, nullable = false)
	private Color color;

	@Enumerated(EnumType.STRING)
	@Column(name = "timezone", length = 10, nullable = false)
	private TimeZone timeZone;

}