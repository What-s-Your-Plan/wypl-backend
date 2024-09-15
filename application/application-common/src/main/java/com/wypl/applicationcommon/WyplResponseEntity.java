package com.wypl.applicationcommon;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

public class WyplResponseEntity<T> extends ResponseEntity<WyplResponseEntity.WyplBody<T>> {

	@Builder
	public WyplResponseEntity(T body, String message, HttpStatusCode status) {
		super(new WyplBody<>(body, message), status);
	}

	public static <T> WyplResponseEntity<T> ok(T body, String message) {
		return new WyplResponseEntity<>(body, message, HttpStatus.OK);
	}

	public static <T> WyplResponseEntity<T> ok(String message) {
		return new WyplResponseEntity<>(null, message, HttpStatus.OK);
	}

	public static <T> WyplResponseEntity<T> created(T body, String message) {
		return new WyplResponseEntity<>(body, message, HttpStatus.CREATED);
	}

	public static <T> WyplResponseEntity<T> created(String message) {
		return new WyplResponseEntity<>(null, message, HttpStatus.CREATED);
	}

	@Getter
	protected static class WyplBody<T> {
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private final T body;

		@JsonInclude(JsonInclude.Include.NON_NULL)
		private final String message;

		protected WyplBody(T body, String message) {
			this.body = body;
			this.message = message;
		}
	}
}
