package com.wypl.openweatherclient;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.wypl.openweatherclient.fixture.OpenWeatherFixture;

class OpenWeatherUrlFactoryTest {

	private static final String OPEN_WEATHER_URL = "MOCK_URL";
	private static final String OPEN_WEATHER_KEY = "MOCK_KEY";

	@DisplayName("다양한 날씨 지역 및 옵션에 따른 OpenWeather URL 생성 테스트")
	@EnumSource(OpenWeatherFixture.class)
	@ParameterizedTest
	void createTest(OpenWeatherFixture fixture) {
		/* Given */
		List<String> params = fixture.urlParams();

		/* When */
		String url = OpenWeatherUrlFactory.create(OPEN_WEATHER_URL, OPEN_WEATHER_KEY)
				.weatherRegion(fixture.getWeatherRegion())
				.isMetric(fixture.isMetric())
				.isLangKr(fixture.isLangKr())
				.build();

		/* Then */
		assertThat(url).contains(params);
	}
}
