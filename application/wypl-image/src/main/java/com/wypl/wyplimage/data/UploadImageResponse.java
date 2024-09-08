package com.wypl.wyplimage.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UploadImageResponse(
		@JsonProperty("image_url") String imageUrl
) {
}
