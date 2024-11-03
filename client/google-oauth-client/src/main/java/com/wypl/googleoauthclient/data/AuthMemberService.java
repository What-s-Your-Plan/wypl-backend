package com.wypl.googleoauthclient.data;

import com.wypl.googleoauthclient.domain.AuthMember;

public interface AuthMemberService {
	AuthMember getValidatedMemberId(String accessToken);
}
