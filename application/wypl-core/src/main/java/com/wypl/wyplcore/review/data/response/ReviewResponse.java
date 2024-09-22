package com.wypl.wyplcore.review.data.response;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wypl.jpamongoreviewdomain.review.domain.Review;
import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContent;

import lombok.Builder;

@Builder
public record ReviewResponse(
	@JsonProperty("review_id")
	long reviewId,

	@JsonProperty("created_at")
	LocalDateTime createdAt,

	String title,

	@JsonProperty("thumbnail_content")
	Map<String, ReviewContent> thumbnailContent
) {
	public static ReviewResponse from(Review review, Map<String, ReviewContent> thumbnailContent) {
		return ReviewResponse.builder()
			.createdAt(LocalDateTime.now())
			.reviewId(review.getReviewId())
			.title(review.getTitle())
			.thumbnailContent(thumbnailContent)
			.build();
	}
}
