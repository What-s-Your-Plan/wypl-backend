package com.wypl.wyplcore.review.service;

import org.springframework.transaction.annotation.Transactional;

import com.wypl.wyplcore.review.data.request.ReviewCreateRequest;
import com.wypl.wyplcore.review.data.request.ReviewUpdateRequest;
import com.wypl.wyplcore.review.data.response.ReviewIdResponse;

public interface ReviewModifyService {
	@Transactional
	ReviewIdResponse createReview(int memberId, ReviewCreateRequest reviewCreateRequest);

	@Transactional
	ReviewIdResponse updateReview(int memberId, int reviewId, ReviewUpdateRequest reviewUpdateRequest);

	@Transactional
	ReviewIdResponse deleteReview(int memberId, int reviewId);
}
