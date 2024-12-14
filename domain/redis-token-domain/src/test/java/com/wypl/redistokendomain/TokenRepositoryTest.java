package com.wypl.redistokendomain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wypl.redistokendomain.exception.RedisTokenErrorCode;
import com.wypl.redistokendomain.exception.RedisTokenException;

@SpringBootTest
class TokenRepositoryTest {
	@Autowired
	private TokenRepository tokenRepository;

	@DisplayName("저장된 토큰 정보 없이 테스트한다.")
	@Nested
	class WithoutSaveTest {
		private final String accessToken = "accessToken";

		@DisplayName("엑세스 토큰이 저장되어 있지 않다.")
		@Test
		void checkExistsTokenNullTest() {
			// When
			boolean result = tokenRepository.checkExistsToken(accessToken);

			// Then
			assertThat(result).isEqualTo(false);
		}

		@DisplayName("존재하지 않는 리프레시 토큰 조회로 인해 에러를 던진다.")
		@Test
		void getRefreshTokenNullTest() {
			// When & Then
			assertThatThrownBy(() -> tokenRepository.getRefreshToken(accessToken))
				.isInstanceOf(RedisTokenException.class)
				.hasMessage(RedisTokenErrorCode.TOKEN_IS_NOT_EXISTED.getMessage());
		}

		@DisplayName("존재하지 않는 토큰 삭제로 인해 에러를 던진다.")
		@Test
		void deleteTokenNullTest() {
			// When & Then
			assertThatThrownBy(() -> tokenRepository.deleteToken(accessToken))
				.isInstanceOf(RedisTokenException.class)
				.hasMessage(RedisTokenErrorCode.TOKEN_IS_NOT_EXISTED.getMessage());
		}
	}

	@Nested
	class WithSaveTest {
		private final String accessToken = "accessToken";
		private final String refreshToken = "refreshToken";

		@BeforeEach
		void setUp() {
			tokenRepository.saveToken("accessToken", "refreshToken");
		}

		@DisplayName("토큰이 존재한다.")
		@Test
		void checkExistsTokenTrueTest() {
			// When
			boolean result = tokenRepository.checkExistsToken(accessToken);

			// Then
			assertThat(result).isEqualTo(true);
		}

		@DisplayName("토큰을 Redis에 성공적으로 저장한다.")
		@Test
		void saveTokenSuccessTest() {
			// When
			tokenRepository.saveToken(accessToken, "newToken");
			String result = tokenRepository.getRefreshToken(accessToken);

			// Then
			assertThat(result).isEqualTo("newToken");
		}

		@DisplayName("저장된 리프레시 토큰을 성공적으로 가져온다.")
		@Test
		void getRefreshTokenSuccessTest() {
			// When
			String result = tokenRepository.getRefreshToken(accessToken);

			// Then
			assertThat(result).isEqualTo(refreshToken);
		}

		@DisplayName("토큰을 정상적으로 삭제한다.")
		@Test
		void deleteTokenSuccessTest() {
			// When
			tokenRepository.saveToken("access", "refresh");
			tokenRepository.deleteToken("access");

			// Then
			assertThat(tokenRepository.checkExistsToken("access")).isFalse();
		}

		@AfterEach
		void afterEach() {
			tokenRepository.deleteToken("accessToken");
		}
	}
}