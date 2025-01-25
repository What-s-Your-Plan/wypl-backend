package com.wypl.wyplcore.facade;

import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.wyplcore.auth.data.response.AuthTokensResponse;

public interface AuthMemberFacade {
	public AuthTokensResponse generateToken(final String provider, final String code);
	public AuthTokensResponse reissueToken(final String accessToken, final String refreshToken);
	public void logout(AuthMember authMember);
	public void quitMember(AuthMember authMember);
}
