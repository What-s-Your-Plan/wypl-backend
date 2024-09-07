package com.wypl.wyplimage.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wypl.applicationcore.WyplResponseEntity;
import com.wypl.wyplimage.data.DeleteImageRequest;
import com.wypl.wyplimage.data.UploadImageResponse;
import com.wypl.wyplimage.properties.ImageResponseMessage;
import com.wypl.wyplimage.service.ImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/file")
@RestController
public class ImageController {
	private final ImageService imageService;
	private final ImageResponseMessage imageResponseMessage;

	@PostMapping("/v1/images")
	public WyplResponseEntity<UploadImageResponse> uploadImage(
			@RequestPart("image") final MultipartFile file
	) {
		String savedImageUrl = imageService.saveImage(file);
		return WyplResponseEntity.created(new UploadImageResponse(savedImageUrl), imageResponseMessage.upload());
	}

	@DeleteMapping("/v1/images")
	public WyplResponseEntity<Void> deleteImage(
			@RequestBody DeleteImageRequest request
	) {
		imageService.removeImages(request);
		return WyplResponseEntity.ok("사진 삭제가 정상적으로 처리되었습니다.");
	}
}