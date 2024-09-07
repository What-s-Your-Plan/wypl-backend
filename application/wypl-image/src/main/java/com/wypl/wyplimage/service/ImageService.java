package com.wypl.wyplimage.service;

import org.springframework.web.multipart.MultipartFile;

import com.wypl.wyplimage.data.DeleteImageRequest;

public interface ImageService {
	String saveImage(final MultipartFile file);

	void removeImages(final DeleteImageRequest request);
}
