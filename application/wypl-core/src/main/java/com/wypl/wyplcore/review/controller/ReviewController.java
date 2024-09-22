package com.wypl.wyplcore.review.controller;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.messaging.Message;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wypl.applicationcommon.WyplResponseEntity;
import com.wypl.wyplcore.auth.domain.AuthMember;
import com.wypl.wyplcore.review.data.request.ReviewCreateRequest;
import com.wypl.wyplcore.review.data.request.ReviewType;
import com.wypl.wyplcore.review.data.request.ReviewUpdateRequest;
import com.wypl.wyplcore.review.data.response.ReviewDetailResponse;
import com.wypl.wyplcore.review.data.response.ReviewIdResponse;
import com.wypl.wyplcore.review.data.response.ReviewListResponse;
import com.wypl.wyplcore.review.service.ReviewModifyService;
import com.wypl.wyplcore.review.service.ReviewReadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

	private final ReviewModifyService reviewModifyService;
	private final ReviewReadService reviewReadService;

	@PostMapping
	public WyplResponseEntity<ReviewIdResponse> createReview(
		@Authenticated AuthMember authMember,
		@RequestBody ReviewCreateRequest reviewCreateRequest
	) {
		ReviewIdResponse response = reviewModifyService.createReview(authMember.id(), reviewCreateRequest);
		return WyplResponseEntity.created(response, "리뷰 등록에 성공했습니다.");
	}

	@PatchMapping("/{reviewId}")
	public WyplResponseEntity<ReviewIdResponse> updateReview(
		@Authenticated AuthMember authMember,
		@PathVariable int reviewId,
		@RequestBody ReviewUpdateRequest reviewUpdateRequest
	) {
		ReviewIdResponse response = reviewModifyService.updateReview(authMember.id(), reviewId, reviewUpdateRequest);
		return WyplResponseEntity.ok(response, "리뷰 수정에 성공했습니다.");
	}

	@DeleteMapping("/{reviewId}")
	public WyplResponseEntity<ReviewIdResponse> deleteReview(
		@Authenticated AuthMember authMember,
		@PathVariable("reviewId") int reviewId
	) {
		ReviewIdResponse response = reviewModifyService.deleteReview(authMember.id(), reviewId);
		return WyplResponseEntity.ok(response, "리뷰 삭제에 성공 했습니다.");
	}

	@GetMapping("/detail/{reviewId}")
	public WyplResponseEntity<ReviewDetailResponse> getDetailReview(
		@Authenticated AuthMember authMember,
		@PathVariable int reviewId
	) {
		ReviewDetailResponse response = reviewReadService.getDetailReview(authMember.id(), reviewId);
		return WyplResponseEntity.ok(response, "리뷰 상세 조회에 성공했습니다.");
	}

	@GetMapping("/{type}")
	public WyplResponseEntity<ReviewListResponse> getReviewsByMemberId(
		@Authenticated AuthMember authMember,
		@PathVariable("type") ReviewType reviewType,
		@RequestParam(value = "lastReviewId", required = false) Long lastReviewId,
		@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
		@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
	) {
		ReviewListResponse response = reviewReadService.getReviews(authMember.id(), lastReviewId, reviewType, startDate,
			endDate);
		return WyplResponseEntity.ok(response, "리뷰 목록 조회에 성공했습니다.");
	}

	@GetMapping("/{type}/{scheduleId}")
	public WyplResponseEntity<ReviewListResponse> getReviewsBySchedule(
		@Authenticated AuthMember authMember,
		@PathVariable("type") ReviewType reviewType,
		@PathVariable("scheduleId") long scheduleId
	) {
		ReviewListResponse response = reviewReadService.getReviewsByScheduleId(authMember.id(), scheduleId,
			reviewType);
		return WyplResponseEntity.ok(response, "일정 별 리뷰 조회에 성공했습니다.");
	}
}
