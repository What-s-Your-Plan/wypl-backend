package com.wypl.wyplimage.exception;

import com.wypl.common.exception.ServerErrorCode;

import lombok.Getter;

@Getter
public enum ImageErrorCode implements ServerErrorCode {
	NOT_ALLOWED_EXTENSION(400, "IMAGE_001", "이미지의 확장자가 잘못되었습니다."),
	FAILURE_DELETE_FILE(500, "IMAGE_002", "정상적으로 요청을 처리하지 못하였습니다."),
	ORIGINAL_IMAGE_PREPARE_ERROR(500, "IMAGE_003", "이미지 처리중 오류가 발생했습니다."),
	INVALID_IMAGE_PATH(500, "IMAGE_004", "이미지 처리중 오류가 발생했습니다."),
	INVALID_FILE_PATH(500, "IMAGE_005", "이미지 처리중 오류가 발생했습니다."),
	NOT_EXISTED_COMMAND(500, "IMAGE_006", "이미지 처리중 오류가 발생했습니다."),
	PARENT_PROCESS_DEAD(500, "IMAGE_007", "정상적으로 요청을 처리하지 못하였습니다.")
	;
	private final int statusCode;
	private final String errorCode;
	private final String message;

	ImageErrorCode(int statusCode, String errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
}
