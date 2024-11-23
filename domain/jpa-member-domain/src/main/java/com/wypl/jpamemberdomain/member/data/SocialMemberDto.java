package com.wypl.jpamemberdomain.member.data;

import com.wypl.jpamemberdomain.member.OauthProvider;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialMemberDto {
	OauthProvider oauthProvider;
	String oauthId;
}
