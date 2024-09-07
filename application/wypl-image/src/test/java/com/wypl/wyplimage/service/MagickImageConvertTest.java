package com.wypl.wyplimage.service;

import static org.mockito.BDDMockito.*;

import java.io.File;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wypl.wyplimage.fixture.ImageFixture;
import com.wypl.wyplimage.image.ImageMagickConvert;
import com.wypl.wyplimage.properties.DiskProperties;
import com.wypl.wyplimage.utils.ImageRemoveUtils;

@ExtendWith(MockitoExtension.class)
class MagickImageConvertTest {

	@InjectMocks
	private ImageMagickConvert imageMagickConvert;

	@Mock
	private DiskProperties properties;

	private File avifImageFile;

	@BeforeEach
	void setUp() {
		given(properties.getAbsolutePath()).willReturn("src/test/magick");
	}

	@DisplayName("MagickImage 를 사용하여 이미지를 변환 및 압축한다.")
	@ParameterizedTest
	@EnumSource(ImageFixture.class)
	void imageTest(ImageFixture fixture) {
		/* When & Then */
		Assertions.assertThatCode(
						() -> avifImageFile = imageMagickConvert.imageConvert(fixture.getMockMultipartFile()))
				.doesNotThrowAnyException();

		/* After */
		ImageRemoveUtils.removeImages(avifImageFile);
	}
}