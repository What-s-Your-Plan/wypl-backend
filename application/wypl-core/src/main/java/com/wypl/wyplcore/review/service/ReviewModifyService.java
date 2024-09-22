package com.wypl.wyplcore.review.service;

import org.springframework.transaction.annotation.Transactional;

import com.wypl.wyplcore.review.data.request.ReviewCreateRequest;
import com.wypl.wyplcore.review.data.request.ReviewUpdateRequest;
import com.wypl.wyplcore.review.data.response.ReviewIdResponse;

public interface ReviewModifyService {
	@Transactional
	ReviewIdResponse createReview(long memberId, ReviewCreateRequest reviewCreateRequest);

	@Transactional
	ReviewIdResponse updateReview(long memberId, long reviewId, ReviewUpdateRequest reviewUpdateRequest);

	@Transactional
	ReviewIdResponse deleteReview(long memberId, long reviewId);
}
