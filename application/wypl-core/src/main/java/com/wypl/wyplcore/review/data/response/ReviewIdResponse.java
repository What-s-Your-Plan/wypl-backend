package com.wypl.wyplcore.review.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReviewIdResponse(
	@JsonProperty("review_id")
	long reviewId
) {
	public static ReviewIdResponse from(int reviewId) {
		return new ReviewIdResponse(reviewId);
	}
}
