package com.wypl.redistokendomain;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TokenRepository {
	private final RedisTemplate<byte[], byte[]> redisTokenTemplate;

	@Transactional
	public void saveToken(String accessToken, String refreshToken) {
		redisTokenTemplate.opsForValue().set(accessToken.getBytes(), refreshToken.getBytes());
		redisTokenTemplate.expire(accessToken.getBytes(), 1, TimeUnit.HOURS);
	}
}
