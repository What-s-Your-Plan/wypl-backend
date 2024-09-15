package com.wypl.wyplcore.review.service;

import java.time.LocalDate;

import com.wypl.wyplcore.review.data.request.ReviewType;
import com.wypl.wyplcore.review.data.response.ReviewDetailResponse;
import com.wypl.wyplcore.review.data.response.ReviewListResponse;

public interface ReviewReadService {
	ReviewDetailResponse getDetailReview(int memberId, int reviewId);

	ReviewListResponse getReviews(int memberId, Integer lastReviewId, ReviewType reviewType, LocalDate startDate,
		LocalDate endDate);

	ReviewListResponse getReviewsByScheduleId(int memberId, int scheduleId, ReviewType reviewType);
}
