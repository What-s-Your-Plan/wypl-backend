package com.wypl.wyplimage.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.wypl.common.exception.CallConstructorException;
import com.wypl.wyplimage.exception.ImageErrorCode;
import com.wypl.wyplimage.exception.ImageException;

import lombok.Generated;

public class ImageRemoveUtils {

	@Generated
	private ImageRemoveUtils() {
		throw new CallConstructorException();
	}

	/**
	 *	해당 파일이 있는 위치의 파일과 폴더를 삭제합니다.
	 *
	 * @param file    삭제 요청하는 파일
	 *
	 * @throws com.wypl.common.exception.WyplException 파일 삭제에 실패하면 예외를 던진다.
	 */
	public static void removeImages(final File file) {
		try {
			Path directoryPath = Paths.get(file.getAbsolutePath().replace(file.getName(), ""));
			deleteDirectoryRecursively(directoryPath);
		} catch (IOException e) {
			throw new ImageException(ImageErrorCode.FAILURE_DELETE_FILE);
		}
	}

	private static void deleteDirectoryRecursively(Path path) throws IOException {
		if (Files.isDirectory(path)) {
			try (var stream = Files.list(path)) {
				for (Path subPath : (Iterable<Path>)stream::iterator) {
					deleteDirectoryRecursively(subPath);
				}
			}
			Files.delete(path);
		} else {
			Files.delete(path);
		}
	}
}