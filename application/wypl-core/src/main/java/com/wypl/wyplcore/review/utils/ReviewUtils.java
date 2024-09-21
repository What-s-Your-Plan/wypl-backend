package com.wypl.wyplcore.review.utils;

import com.wypl.common.exception.CallConstructorException;
import com.wypl.jpamongoreviewdomain.review.domain.Review;
import com.wypl.jpamongoreviewdomain.review.repository.ReviewRepository;
import com.wypl.wyplcore.review.exception.ReviewErrorCode;
import com.wypl.wyplcore.review.exception.ReviewException;

import lombok.Generated;

public class ReviewUtils {

	@Generated
	private ReviewUtils() {throw new CallConstructorException();
	}

	public static Review findReviewByReviewIdAndMemberId(ReviewRepository reviewRepository, long reviewId, long memberId) {
		return reviewRepository.findByReviewIdAndMemberId(reviewId, memberId)
			.orElseThrow(() -> new ReviewException(ReviewErrorCode.NO_SUCH_REVIEW));
	}
}
