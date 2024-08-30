package com.wypl.imageserver.data.request;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeleteImageRequest(
		@JsonProperty("image_url_list")
		ArrayList<String> imageUrlList
) {
}
