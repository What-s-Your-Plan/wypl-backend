package com.wypl.wyplcore.token.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wypl.redistokendomain.TokenRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TokenService {
	private final TokenRepository tokenRepository;

	public boolean checkExistsToken(String accessToken) {
		return tokenRepository.checkExistsToken(accessToken);
	}

	@Transactional
	public void saveToken(String accessToken, String refreshToken) {
		tokenRepository.saveToken(accessToken, refreshToken);
	}

	public String getRefreshToken(String accessToken) {
		return tokenRepository.getRefreshToken(accessToken);
	}

	@Transactional
	public void deleteToken(String accessToken) {
		System.out.println("너가 문제니?");
		tokenRepository.deleteToken(accessToken);
		System.out.println("너가 문제구나!!");
	}
}
