package com.wypl.openweatherclient;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.wypl.openweatherclient.config.OpenWeatherProperties;
import com.wypl.openweatherclient.data.OpenWeatherCond;
import com.wypl.openweatherclient.data.OpenWeatherResponse;
import com.wypl.openweatherclient.exception.OpenWeatherErrorCode;
import com.wypl.openweatherclient.exception.OpenWeatherException;
import com.wypl.openweatherclient.fixture.OpenWeatherFixture;
import com.wypl.openweatherclient.fixture.OpenWeatherResponseFixture;

@ExtendWith(MockitoExtension.class)
class OpenWeatherClientTest {
	@InjectMocks
	private OpenWeatherClientImpl openWeatherClient;

	@Mock
	private OpenWeatherProperties properties;

	@Mock
	private RestTemplate restTemplate;

	@BeforeEach
	void propertiesSetUp() {
		given(properties.getBaseUrl()).willReturn("MOCK_URL");
		given(properties.getKey()).willReturn("MOCK_KEY");
	}

	@DisplayName("정상 응답(2xx)일 때 예외가 발생하지 않는지 검증")
	@Test
	void status2xxTest() {
		/* Given */
		given(restTemplate.getForEntity(anyString(), eq(OpenWeatherResponse.class)))
				.willReturn(OpenWeatherResponseFixture.KOREA_KR_CELSIUS.to2xxResponse());

		/* When & Then */
		assertThatCode(() -> openWeatherClient.fetchWeather(OpenWeatherFixture.KOREA.toOpenWeatherCond()))
				.doesNotThrowAnyException();
	}

	@DisplayName("알수 없는 오류로 인한 OpenWeatherException 발생 여부 검증")
	@Test
	void statusErrorTest() {
		/* Given */
		OpenWeatherCond openWeatherCond = OpenWeatherFixture.KOREA.toOpenWeatherCond();
		given(restTemplate.getForEntity(anyString(), eq(OpenWeatherResponse.class)))
				.willReturn(OpenWeatherResponseFixture.KOREA_KR_CELSIUS.to3xxResponse());

		/* When & Then */
		assertThatThrownBy(() -> openWeatherClient.fetchWeather(openWeatherCond))
				.isInstanceOf(OpenWeatherException.class)
				.hasMessageContaining(OpenWeatherErrorCode.INVALID_OPEN_WEATHER_REQUEST.getMessage());
	}

	@DisplayName("잘못된 요청으로 인한(5xx) 시 OpenWeatherException 발생 여부 검증")
	@Test
	void status5xxTest() {
		/* Given */
		OpenWeatherCond openWeatherCond = OpenWeatherFixture.KOREA.toOpenWeatherCond();
		given(restTemplate.getForEntity(anyString(), eq(OpenWeatherResponse.class)))
				.willReturn(OpenWeatherResponseFixture.KOREA_KR_CELSIUS.to5xxResponse());

		/* When & Then */
		assertThatThrownBy(() -> openWeatherClient.fetchWeather(openWeatherCond))
				.isInstanceOf(OpenWeatherException.class)
				.hasMessageContaining(OpenWeatherErrorCode.INTERNAL_SERVER_ERROR.getMessage());
	}
}