package com.wypl.wyplimage.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wypl.awss3client.AwsS3StorageService;
import com.wypl.wyplimage.data.DeleteImageRequest;
import com.wypl.wyplimage.exception.ImageErrorCode;
import com.wypl.wyplimage.exception.ImageException;
import com.wypl.wyplimage.image.ImageConvertible;
import com.wypl.wyplimage.utils.ImageRemoveUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
	private final ImageConvertible imageConvertible;
	private final AwsS3StorageService awsS3StorageService;

	/**
	 *	사용자가 요청한 이미지를 업로드한 뒤 업로드된 이미지의 {@code URL}을 반환한다.<p>
	 * 	</br>
	 *    {@code ImageException}<p>
	 *	1. {@link #validateImageExtension(MultipartFile)}	이미지의 확장자가 잘못되었으면 예외를 던진다.
	 *
	 * @param file    사용자가 업로드 요청한 이미지 파일
	 * @return 업로드한 이미지의 URL
	 */
	@Override
	public String saveImage(final MultipartFile file) {
		validateImageExtension(file);
		File avifImage = imageConvertible.imageConvert(file);
		String uploadImageUrl = awsS3StorageService.fileUpload(avifImage);
		ImageRemoveUtils.removeImages(avifImage);
		return uploadImageUrl;
	}

	private void validateImageExtension(final MultipartFile file) {
		String originalFileName = Optional.ofNullable(file.getOriginalFilename())
				.orElseThrow();
		String extension = originalFileName.substring(
						originalFileName.lastIndexOf("."))
				.toLowerCase();
		if (ImageExtension.notContains(extension)) {
			throw new ImageException(ImageErrorCode.NOT_ALLOWED_EXTENSION);
		}
	}

	/**
	 *	삭제 요청한 이미지의 URL 목록을 가지고 삭제한다.<p>
	 *	</br>
	 *    {@link IndexOutOfBoundsException}<p>
	 *	1. 올바르지 않은 파일의 이름인 경우 예외를 던진다.
	 *
	 * @param request 삭제 요청한 이미지의 URL 목록
	 */
	@Override
	public void removeImages(final DeleteImageRequest request) {
		List<String> imageNames = request.imageUrlList().stream()
				.map(imageUrl -> imageUrl.substring(imageUrl.lastIndexOf("/") + 1))
				.toList();
		awsS3StorageService.filesRemove(imageNames);
	}

	private static class ImageExtension {
		private static final Set<String> ALLOWED_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png", ".gif", ".avif");

		public static boolean notContains(final String extension) {
			return !ALLOWED_EXTENSIONS.contains(extension);
		}
	}
}
