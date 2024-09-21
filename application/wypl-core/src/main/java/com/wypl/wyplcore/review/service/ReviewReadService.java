package com.wypl.wyplcore.review.service;

import java.time.LocalDate;

import com.wypl.wyplcore.review.data.request.ReviewType;
import com.wypl.wyplcore.review.data.response.ReviewDetailResponse;
import com.wypl.wyplcore.review.data.response.ReviewListResponse;

public interface ReviewReadService {
	ReviewDetailResponse getDetailReview(long memberId, long reviewId);

	ReviewListResponse getReviews(long memberId, Long lastReviewId, ReviewType reviewType, LocalDate startDate,
		LocalDate endDate);

	ReviewListResponse getReviewsByScheduleId(long memberId, long scheduleId, ReviewType reviewType);
}
