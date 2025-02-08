package com.wypl.jpamemberdomain.member.domain;

import java.time.LocalDate;

import com.wypl.common.Color;
import com.wypl.jpacommon.JpaBaseEntity;
import com.wypl.jpamemberdomain.member.TimeZone;
import com.wypl.jpamemberdomain.member.data.MemberSaveDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	//	@OneToMany(mappedBy = "member")
	//	private List<MemberCalendar> memberCalendars;

	public static Member of(MemberSaveDto memberSaveDto) {
		return Member.builder()
			.email(memberSaveDto.getEmail())
			.nickname(memberSaveDto.getNickname())
			.birthday(memberSaveDto.getBirthday())
			.profileImage(memberSaveDto.getProfileImage())
			.color(Color.labelBrown)
			.timeZone(TimeZone.KOREA)
			.build();
	}

}
