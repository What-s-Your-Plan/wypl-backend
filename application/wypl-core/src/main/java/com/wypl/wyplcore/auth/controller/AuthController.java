package com.wypl.wyplcore.auth.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wypl.applicationcommon.WyplResponseEntity;
import com.wypl.googleoauthclient.annotation.Authenticated;
import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.wyplcore.auth.data.response.AuthTokensResponse;
import com.wypl.wyplcore.auth.service.AuthServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {
	private final AuthServiceImpl authService;

	@PostMapping("/v1/sign-in/{provider}")
	public WyplResponseEntity<AuthTokensResponse> signIn(
		@PathVariable("provider") String provider,
		@RequestParam("code") String code
	) {
		AuthTokensResponse response = authService.generateToken(provider, code);
		return WyplResponseEntity.ok(response, "로그인에 성공하였습니다.");
	}

	@PutMapping("/v1/reissue")
	public WyplResponseEntity<AuthTokensResponse> reissue(
		@RequestParam("access_token") String accessToken,
		@RequestParam("refresh_token") String refreshToken
	) {
		AuthTokensResponse response = authService.reissueToken(accessToken, refreshToken);
		return WyplResponseEntity.created(response, "토큰 재발급에 성공하였습니다.");
	}

	@DeleteMapping("/v1/logout")
	public WyplResponseEntity<Void> logout(
		@Authenticated AuthMember authMember
	) {
		authService.logout(authMember);
		return WyplResponseEntity.ok("로그아웃에 성공하였습니다.");
	}
}
