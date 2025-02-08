package com.wypl.redistokendomain;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.wypl.redistokendomain.exception.RedisTokenErrorCode;
import com.wypl.redistokendomain.exception.RedisTokenException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TokenRepository {
	private final RedisTemplate<byte[], byte[]> redisTokenTemplate;

	public boolean checkExistsToken(String accessToken) {
		byte[] refreshToken = redisTokenTemplate.opsForValue().get(accessToken.getBytes());
		return refreshToken != null;
	}

	public void saveToken(String accessToken, String refreshToken) {
		redisTokenTemplate.opsForValue().set(accessToken.getBytes(), refreshToken.getBytes(), 1, TimeUnit.HOURS);
	}

	public String getRefreshToken(String accessToken) {
		byte[] refreshToken = redisTokenTemplate.opsForValue().get(accessToken.getBytes());

		if (refreshToken == null) {
			throw new RedisTokenException(RedisTokenErrorCode.TOKEN_IS_NOT_EXISTED);
		}

		return new String(refreshToken);
	}

	public void deleteToken(String accessToken) {
		if (Boolean.FALSE.equals(redisTokenTemplate.delete(accessToken.getBytes()))) {
			throw new RedisTokenException((RedisTokenErrorCode.TOKEN_IS_NOT_EXISTED));
		}
	}
}
