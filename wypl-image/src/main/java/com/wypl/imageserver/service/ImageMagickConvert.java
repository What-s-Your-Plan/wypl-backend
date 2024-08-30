package com.wypl.imageserver.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.wypl.imageserver.global.exception.GlobalErrorCode;
import com.wypl.imageserver.global.exception.GlobalException;
import com.wypl.imageserver.global.exception.ImageMagickErrorCode;
import com.wypl.imageserver.global.exception.ImageMagickException;
import com.wypl.imageserver.properties.DiskProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ImageMagickConvert implements ImageConvertible {

	private static final int SUCCESS_EXIT_NUMBER = 0;

	private final DiskProperties diskProperties;

	/**
	 *	이미지를 `avif`확장자로 변환합니다.<p>
	 *	하위 메서드에서 예외를 던지는 경우는 다음과 같습니다.<p>
	 * </br>
	 *    {@link  IOException}<p>
	 *	1. {@link #prepareOriginalImage} 원본 이미지 사전작업중 임시 디렉토리를 생성하지 못하거나 파일을 디스크로 복사하지 못하면 예외를 던진다.<p>
	 *	2. {@link #imageConvertProcess} magick 명령어가 잘못되면 예외를 던진다.<p>
	 * </br>
	 *    {@link  InterruptedException}<p>
	 *	1. {@link #imageConvertProcess} 작업중인 부모 프로세스가 죽어서 멈추면 예외를 던진다.
	 *
	 * @param file 변환하는 원본 이미지
	 * @return `avif`로 변환된 이미지
	 */
	@Override
	public File imageConvert(final MultipartFile file) {
		String uuid = UUID.randomUUID().toString();
		Path originalImagePath = prepareOriginalImage(uuid, file);
		Path avifImagePath = prepareAvifImage(uuid);
		imageConvertProcess(originalImagePath, avifImagePath);
		return avifImagePath.toFile();
	}

	private Path prepareOriginalImage(final String uuid, final MultipartFile file) {
		try {
			Path workingDirectory = Paths.get(diskProperties.getAbsolutePath(), uuid);
			Files.createDirectories(workingDirectory);
			Path originalImagePath = workingDirectory.resolve(Objects.requireNonNull(file.getOriginalFilename()));
			Files.copy(file.getInputStream(), originalImagePath);
			return originalImagePath;
		} catch (IOException e) {
			throw new ImageMagickException(ImageMagickErrorCode.ORIGINAL_IMAGE_PREPARE_ERROR);
		}
	}

	private Path prepareAvifImage(final String uuid) {
		Path workingDirectory = Paths.get(diskProperties.getAbsolutePath(), uuid);
		String avifImageName = uuid + ".avif";
		return workingDirectory.resolve(avifImageName);
	}

	private void imageConvertProcess(Path originalImagePath, Path avifImagePath) {
		ProcessBuilder processBuilder = new ProcessBuilder(
				"convert",
				originalImagePath.toAbsolutePath().toString(),
				"-quality", "50",
				avifImagePath.toAbsolutePath().toString()
		);
		try {
			Process process = processBuilder.start();
			if (process.waitFor() != SUCCESS_EXIT_NUMBER) {
				throw new ImageMagickException(ImageMagickErrorCode.INVALID_FILE_PATH);
			}
		} catch (IOException e) {
			throw new ImageMagickException(ImageMagickErrorCode.NOT_EXISTED_COMMAND);
		} catch (InterruptedException e) {
			throw new GlobalException(GlobalErrorCode.PARENT_PROCESS_DEAD); // 작업 처리중 부모 프로세스가 죽는 경우
		}
	}
}
