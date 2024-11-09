package com.wypl.googleoauthclient.service;

import com.wypl.googleoauthclient.domain.AuthMember;

public interface AuthMemberService {
	AuthMember getValidatedMemberId(String accessToken);
}
