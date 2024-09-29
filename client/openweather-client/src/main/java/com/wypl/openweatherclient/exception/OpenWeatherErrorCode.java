package com.wypl.openweatherclient.exception;

import com.wypl.common.exception.ServerErrorCode;

import lombok.Getter;

@Getter
public enum OpenWeatherErrorCode implements ServerErrorCode {
	INVALID_OPEN_WEATHER_REQUEST(500, "OPEN_WEATHER_001", "외부 서비스의 오류입니다."),
	INTERNAL_SERVER_ERROR(400, "OPEN_WEATHER_002", "올바르지 않은 요청입니다."),
	;

	private final int statusCode;
	private final String errorCode;
	private final String message;

	OpenWeatherErrorCode(int statusCode, String errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
}
