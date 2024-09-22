package com.wypl.wyplcore.review.data.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.jpamongoreviewdomain.review.domain.Review;
import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContent;

import lombok.Builder;

@Builder
public record ReviewDetailResponse(
	@JsonProperty("review_id")
	long reviewId,

	String title,

	// Todo : import 후 주석 해제
	// ScheduleResponse schedule,

	List<Map<String, ReviewContent>> contents
) {
	public static ReviewDetailResponse of(Review review, Schedule schedule,
		List<Map<String, ReviewContent>> reviewContents) {
		return ReviewDetailResponse.builder()
			.reviewId(review.getReviewId())
			.title(review.getTitle())
			// Todo : import 후 주석 해제
			// .schedule(ScheduleResponse.from(schedule))
			.contents(reviewContents)
			.build();
	}
}
