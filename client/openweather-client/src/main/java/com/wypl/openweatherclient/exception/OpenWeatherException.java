package com.wypl.openweatherclient.exception;

import com.wypl.common.exception.WyplException;

public class OpenWeatherException extends WyplException {
	public OpenWeatherException(OpenWeatherErrorCode serverErrorCode) {
		super(serverErrorCode);
	}
}
