package com.wypl.wyplimage.image;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface ImageConvertible {
	File imageConvert(final MultipartFile multipartFile);
}
