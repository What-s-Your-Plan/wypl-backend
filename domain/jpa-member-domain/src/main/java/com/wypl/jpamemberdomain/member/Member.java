package com.wypl.jpamemberdomain.member;

import jakarta.persistence.*;

import java.awt.*;
import java.time.LocalDate;

@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	@OneToMany(mappedBy = "member")
	private List<MemberCalendar> memberCalendars;

}
