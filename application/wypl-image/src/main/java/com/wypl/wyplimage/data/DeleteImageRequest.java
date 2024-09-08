package com.wypl.wyplimage.data;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeleteImageRequest(
		@JsonProperty("image_url_list")
		ArrayList<String> imageUrlList
) {
}
