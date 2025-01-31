package com.wypl.wyplcore.token.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wypl.redistokendomain.TokenRepository;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
	@InjectMocks
	private TokenService tokenService;
	@Mock
	private TokenRepository tokenRepository;

	@DisplayName("Redis에 존재하는 토큰인지 확인한다.")
	@Test
	void checkExistsTokenTest() {
		// When
		tokenService.checkExistsToken(anyString());

		// Then
		verify(tokenRepository).checkExistsToken(anyString());
	}

	@DisplayName("Redis에 토큰을 저장한다.")
	@Test
	void saveTokenTest() {
		// When
		tokenService.saveToken(anyString(), anyString());

		// Then
		verify(tokenRepository).saveToken(anyString(), anyString());
	}

	@DisplayName("Redis에서 Access Token으로 Refresh Token 값을 가져온다.")
	@Test
	void getRefreshTokenTest() {
		// When
		tokenService.getRefreshToken(anyString());

		// Then
		verify(tokenRepository).getRefreshToken(anyString());
	}

	@DisplayName("Redis에 저장된 토큰 정보를 삭제한다.")
	@Test
	void deleteToken() {
		// When
		tokenService.deleteToken(anyString());

		// Then
		verify(tokenRepository).deleteToken(anyString());
	}
}