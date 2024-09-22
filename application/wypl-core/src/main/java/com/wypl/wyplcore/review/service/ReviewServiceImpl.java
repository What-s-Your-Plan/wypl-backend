package com.wypl.wyplcore.review.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wypl.jpacalendardomain.schedule.domain.Schedule;
import com.wypl.jpamemberdomain.member.Member;
import com.wypl.jpamongoreviewdomain.review.domain.Review;
import com.wypl.jpamongoreviewdomain.review.repository.ReviewRepository;
import com.wypl.jpamongoreviewdomain.reviewcontents.domain.BlockType;
import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContent;
import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContents;
import com.wypl.jpamongoreviewdomain.reviewcontents.repository.ReviewContentsRepository;
import com.wypl.wyplcore.review.data.request.ReviewCreateRequest;
import com.wypl.wyplcore.review.data.request.ReviewType;
import com.wypl.wyplcore.review.data.request.ReviewUpdateRequest;
import com.wypl.wyplcore.review.data.response.ReviewDetailResponse;
import com.wypl.wyplcore.review.data.response.ReviewIdResponse;
import com.wypl.wyplcore.review.data.response.ReviewListResponse;
import com.wypl.wyplcore.review.data.response.ReviewResponse;
import com.wypl.wyplcore.review.exception.ReviewErrorCode;
import com.wypl.wyplcore.review.exception.ReviewException;
import com.wypl.wyplcore.review.utils.ReviewUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewModifyService, ReviewReadService {
	private final ReviewRepository reviewRepository;

	// private final ScheduleRepository scheduleRepository;

	private final ReviewContentsRepository reviewContentsRepository;

	@Override
	@Transactional
	public ReviewIdResponse createReview(long memberId, ReviewCreateRequest reviewCreateRequest) {
		validateReviewContents(reviewCreateRequest.contents(), reviewCreateRequest.title());

		//schedule, member 유효성 판단
		// MemberSchedule memberSchedule = memberScheduleService.getMemberScheduleByMemberAndSchedule(memberId,
		// 	ScheduleServiceUtils.findById(scheduleRepository, reviewCreateRequest.scheduleId()));
		// memberSchedule.writeReview();

		Member findMember = null;    // FIXME: MemberUtil
		Schedule findSchedule = null;    // FIXME: ScheduleUtil

		Review review = Review.of(reviewCreateRequest.title(), findMember, findSchedule);
		Review savedReview = reviewRepository.save(review);

		reviewContentsRepository.save(ReviewContents.of(savedReview.getReviewId(), reviewCreateRequest.contents()));
		return ReviewIdResponse.from(savedReview.getReviewId());
	}

	@Override
	@Transactional
	public ReviewIdResponse updateReview(long memberId, long reviewId, ReviewUpdateRequest reviewUpdateRequest) {
		validateReviewContents(reviewUpdateRequest.contents(), reviewUpdateRequest.title());

		Member member = null;	// Todo : 조회

		Review review = ReviewUtils.findByReviewIdAndMember(reviewRepository, reviewId, member);
		review.updateTitle(reviewUpdateRequest.title());

		ReviewContents reviewContents = reviewContentsRepository.findByReviewIdAndDeletedAtNull(review.getReviewId());
		reviewContents.updateContents(reviewUpdateRequest.contents());

		reviewContentsRepository.save(reviewContents);

		return ReviewIdResponse.from(review.getReviewId());
	}

	@Override
	@Transactional
	public ReviewIdResponse deleteReview(long memberId, long reviewId) {
		Member member = null;	// Todo : 조회
		Review review = ReviewUtils.findByReviewIdAndMember(reviewRepository, reviewId, member);

		ReviewContents reviewContents = reviewContentsRepository.findByReviewIdAndDeletedAtNull(review.getReviewId());
		reviewContents.delete();

		reviewContentsRepository.save(reviewContents);

		review.delete();

		return ReviewIdResponse.from(review.getReviewId());
	}

	@Override
	public ReviewDetailResponse getDetailReview(long memberId, long reviewId) {
		Member member = null;	// Todo : 조회
		Review review = ReviewUtils.findByReviewIdAndMember(reviewRepository, reviewId, member);
		Schedule schedule = review.getSchedule();

		// Todo : 저장할 때 null 검사 하는데, 가져올 때도 검사해야 할까???
		ReviewContents reviewContents = reviewContentsRepository.findByReviewIdAndDeletedAtNull(review.getReviewId());

		return ReviewDetailResponse.of(review, schedule, reviewContents.getContents());
	}

	@Override
	public ReviewListResponse getReviews(long memberId, Long lastReviewId, ReviewType reviewType, LocalDate startDate,
		LocalDate endDate) {

		List<Review> reviews = switch (reviewType) {
			case NEWEST -> {
				if (startDate == null || endDate == null) {
					if (lastReviewId == null) {
						yield reviewRepository.getReviewsNewestAll(memberId);
					}
					yield reviewRepository.getReviewsNewestAllAfter(memberId, lastReviewId);
				}

				LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.of(0, 0));
				LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59));

				if (lastReviewId == null) {
					yield reviewRepository.getReviewsNewest(memberId, startDateTime, endDateTime);
				}

				yield reviewRepository.getReviewsNewestAfter(memberId, lastReviewId, startDateTime, endDateTime);
			}
			case OLDEST -> {

				if (startDate == null || endDate == null) {
					if (lastReviewId == null) {
						lastReviewId = 0L;
					}
					yield reviewRepository.getReviewsOldestAll(memberId, lastReviewId);
				}
				LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.of(0, 0));
				LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59));

				if (lastReviewId == null) {
					lastReviewId = 0L;
				}

				yield reviewRepository.getReviewsOldest(memberId, lastReviewId, startDateTime, endDateTime);
			}
		};

		return ReviewListResponse.from(reviews.stream().map(
			review -> ReviewResponse.builder()
				.title(review.getTitle())
				.createdAt(review.getCreatedAt())
				.reviewId(review.getReviewId())
				.thumbnailContent(getThumbnailContent(
					reviewContentsRepository.findByReviewIdAndDeletedAtNull(review.getReviewId())
						.getContents()))
				.build()
		).toList());
	}

	@Override
	public ReviewListResponse getReviewsByScheduleId(long memberId, long scheduleId, ReviewType reviewType) {
		// MemberSchedule memberSchedule = memberScheduleService.getMemberScheduleByMemberAndSchedule(memberId,
		// 	ScheduleServiceUtils.findById(scheduleRepository, scheduleId));

		Member member = null;		// Todo : 조회
		Schedule schedule = null;	// Todo : 조회

		List<Review> reviews = switch (reviewType) {
			case NEWEST -> {
				yield reviewRepository.getReviewByMemberAndScheduleOrderByCreatedAtDesc(member, schedule);
			}
			case OLDEST -> {
				yield reviewRepository.getReviewsByMemberAndScheduleOrderByCreatedAt(member, schedule);
			}
		};

		List<ReviewResponse> reviewResponses = new ArrayList<>();
		for (Review review : reviews) {
			reviewResponses.add(
				ReviewResponse.builder()
					.reviewId(review.getReviewId())
					.title(review.getTitle())
					.createdAt(review.getCreatedAt())
					.thumbnailContent(
						getThumbnailContent(
							reviewContentsRepository.findByReviewIdAndDeletedAtNull(
								review.getReviewId()).getContents())
					)
					.build()
			);
		}

		return ReviewListResponse.from(reviewResponses);
	}

	private Map<String, ReviewContent> getThumbnailContent(List<Map<String, ReviewContent>> contents) {
		for (Map<String, ReviewContent> content : contents) {
			if (content.get("blockType").getBlockType().equals(BlockType.REVIEW_PICTURE)) {
				return content;
			}
		}

		return contents.get(0);
	}

	private void validateReviewContents(List<Map<String, ReviewContent>> contents, String title) {
		if (contents.isEmpty()) {
			throw new ReviewException(ReviewErrorCode.EMPTY_CONTENTS);
		}

		if (title == null || title.length() > 50 || title.length() == 0) {
			throw new ReviewException(ReviewErrorCode.INVALID_TITLE);
		}
	}
}
