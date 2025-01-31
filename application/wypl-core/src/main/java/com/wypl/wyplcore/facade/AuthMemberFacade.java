package com.wypl.wyplcore.facade;

import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.wyplcore.auth.data.response.AuthTokensResponse;

public interface AuthMemberFacade {
	AuthTokensResponse generateToken(final String provider, final String code);
	AuthTokensResponse reissueToken(final String accessToken, final String refreshToken);
	void logout(final AuthMember authMember);
	void quitMember(AuthMember authMember);
}
