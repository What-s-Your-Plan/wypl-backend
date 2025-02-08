package com.wypl.jpamemberdomain.member.data;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberSaveDto {
	String email;
	String nickname;
	LocalDate birthday;
	String profileImage;
}