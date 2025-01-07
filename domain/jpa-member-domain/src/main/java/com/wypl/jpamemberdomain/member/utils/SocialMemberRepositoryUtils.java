package com.wypl.jpamemberdomain.member.utils;

import com.wypl.jpamemberdomain.member.OauthProvider;
import com.wypl.jpamemberdomain.member.domain.SocialMember;
import com.wypl.jpamemberdomain.member.exception.MemberErrorCode;
import com.wypl.jpamemberdomain.member.exception.MemberException;
import com.wypl.jpamemberdomain.member.repository.SocialMemberRepository;

public class SocialMemberRepositoryUtils {
	public static SocialMember getSocialMember(SocialMemberRepository socialMemberRepository, String id) {
		return socialMemberRepository.findByOauthProviderAndOauthId(OauthProvider.GOOGLE, id)
			.orElseThrow(() -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER));
	}
}
