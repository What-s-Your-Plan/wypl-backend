package com.wypl.imageserver.service;

import org.springframework.web.multipart.MultipartFile;

import com.wypl.imageserver.data.request.DeleteImageRequest;

public interface ImageService {
	String saveImage(final MultipartFile file);

	void removeImages(final DeleteImageRequest request);
}
