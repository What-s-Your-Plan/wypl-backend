package com.wypl.jpamemberdomain.member.data;

import com.wypl.jpamemberdomain.member.OauthProvider;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialMemberSaveDto {
	OauthProvider oauthProvider;
	String oauthId;
}
