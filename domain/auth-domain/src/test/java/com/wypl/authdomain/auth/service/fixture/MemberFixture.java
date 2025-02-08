// package com.wypl.authdomain.auth.service.fixture;
//
// import java.time.LocalDate;
// import java.util.UUID;
//
// import com.wypl.common.Color;
// import com.wypl.jpamemberdomain.member.OauthProvider;
// import com.wypl.jpamemberdomain.member.TimeZone;
// import com.wypl.jpamemberdomain.member.domain.Member;
// import com.wypl.jpamemberdomain.member.domain.SocialMember;
//
// // Todo : Access Level 고민해보자
// // @AllArgsConstructor
// public enum MemberFixture {
// 	JEONG_HOON(1L, "biosjh@gmail.com", "sjh", LocalDate.of(1999, 1, 15), "JHprofile", OauthProvider.GOOGLE)
// 	;
//
// 	private final Long memberId;
// 	private final String email;
// 	private final String nickname;
// 	private final LocalDate birthday;
// 	private final String profileImage;
// 	private final OauthProvider oauthProvider;
//
// 	// @AllArgsConstructor 인식 불가로 인해, 명시적으로 생성자 추가
// 	MemberFixture(Long memberId, String email, String nickname, LocalDate birthday, String profileImage, OauthProvider oauthProvider) {
// 		this.memberId = memberId;
// 		this.email = email;
// 		this.nickname = nickname;
// 		this.birthday = birthday;
// 		this.profileImage = profileImage;
// 		this.oauthProvider = oauthProvider;
// 	}
//
// 	public Member toMember() {
// 		return Member.builder()
// 			.memberId(memberId)
// 			.email(email)
// 			.nickname(nickname)
// 			.birthday(birthday)
// 			.profileImage(profileImage)
// 			.color(Color.labelBrown)
// 			.timeZone(TimeZone.KOREA)
// 			.build();
// 	}
//
// 	public SocialMember toSocialMember() {
// 		return SocialMember.builder()
// 			.id(memberId)
// 			.member(toMember())
// 			.oauthProvider(oauthProvider)
// 			.oauthId(UUID.randomUUID().toString())
// 			.build();
// 	}
// }
