package com.wypl.jpamemberdomain.member.fixture;

import java.time.LocalDate;

import com.wypl.common.Color;
import com.wypl.jpamemberdomain.member.OauthProvider;
import com.wypl.jpamemberdomain.member.TimeZone;
import com.wypl.jpamemberdomain.member.domain.Member;

public enum MemberFixture {
	JEONG_HOON(1L, "biosjh@gmail.com", "sjh", LocalDate.of(1999, 1, 15), "JHprofile", OauthProvider.GOOGLE),
	JEONG_UK(2L, "workju1124@gmail.com", "kimsei1124", LocalDate.of(1998, 11, 24), "JUprofile", OauthProvider.GOOGLE);

	private final Long memberId;
	private final String email;
	private final String nickname;
	private final LocalDate birthday;
	private final String profileImage;
	private final OauthProvider oauthProvider;

	MemberFixture(
		long memberId,
		String email,
		String nickname,
		LocalDate birthday,
		String profileImage,
		OauthProvider oauthProvider
	) {
		this.memberId = memberId;
		this.email = email;
		this.nickname = nickname;
		this.birthday = birthday;
		this.profileImage = profileImage;
		this.oauthProvider = oauthProvider;
	}

	public Member toMember() {
		return Member.builder()
			.memberId(memberId)
			.email(email)
			.nickname(nickname)
			.birthday(birthday)
			.profileImage(profileImage)
			.color(Color.labelBrown)
			.timeZone(TimeZone.KOREA)
			.build();
	}
}

