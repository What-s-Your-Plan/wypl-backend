package com.wypl.wyplimage.fixture;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.mock.web.MockMultipartFile;

public enum ImageFixture {
	AVIF_IMAGE("image", "avif", "image/avif", "src/test/resources/image/image.avif"),
	GIF_IMAGE("image", "gif", "image/gif", "src/test/resources/image/image.gif"),
	JPG_IMAGE("image", "jpg", "image/jpg", "src/test/resources/image/image.jpg"),
	JPEG_IMAGE("image", "jpeg", "image/jpeg", "src/test/resources/image/image.jpeg"),
	PNG_IMAGE("image", "png", "image/png", "src/test/resources/image/image.png");

	private final String name;
	private final String contentType;
	private final String type;
	private final String path;

	ImageFixture(String name, String contentType, String type, String path) {
		this.name = name;
		this.contentType = contentType;
		this.type = type;
		this.path = path;
	}

	public MockMultipartFile getMockMultipartFile() {
		try (FileInputStream fileInputStream = new FileInputStream(path)) {
			return new MockMultipartFile(name, name + "." + contentType, type, fileInputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
