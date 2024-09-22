package com.wypl.jpamongoreviewdomain.review.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wypl.jpacalendardomain.schedule.domain.Schedule;
import com.wypl.jpamemberdomain.member.Member;
import com.wypl.jpamongoreviewdomain.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	Optional<Review> findByReviewIdAndMember(long reviewId, Member member);

	//일정 별 조회
	//오래된순
	List<Review> getReviewsByMemberAndScheduleOrderByCreatedAt(Member member, Schedule schedule);

	//최신순
	List<Review> getReviewByMemberAndScheduleOrderByCreatedAtDesc(Member member, Schedule schedule);

	//리뷰 조회
	//1. 날짜 설정 안한 경우(오래된 순, 모든 리뷰, 무한 스크롤)
	@Query("select r "
		+ "from Review r join fetch r.member m "
		+ "where m.memberId = :member_id and r.reviewId > :last_review_id "
		+ "order by r.reviewId asc "
		+ "limit 24")
	List<Review> getReviewsOldestAll(
		@Param("member_id") long memberId,
		@Param("last_review_id") long lastReviewId
	);

	//2-1. 날짜 설정 안한 경우(최신순, 모든 리뷰, 무한스크롤, 첫번째)
	@Query("select r "
		+ "from Review r join fetch r.member m "
		+ "where m.memberId = :member_id "
		+ "order by r.reviewId desc "
		+ "limit 24")
	List<Review> getReviewsNewestAll(
		@Param("member_id") long memberId
	);

	//2-2. 날짜 설정 안한 경우(최신순, 모든 리뷰, 무한스크롤, 첫번째 이후)
	@Query("select r "
		+ "from Review r join fetch r.member m "
		+ "where m.memberId = :member_id and r.reviewId < :last_review_id "
		+ "order by r.reviewId desc "
		+ "limit 24")
	List<Review> getReviewsNewestAllAfter(
		@Param("member_id") long memberId,
		@Param("last_review_id") long lastReviewId
	);

	//3. 날짜 설정한 경우(오래된 순, 무한 스크롤)
	@Query("select r "
		+ "from Review r join fetch r.member m "
		+ "where m.memberId = :member_id and r.reviewId > :last_review_id and r.createdAt between :start_date and :end_date "
		+ "order by r.reviewId asc "
		+ "limit 24")
	List<Review> getReviewsOldest(
		@Param("member_id") long memberId,
		@Param("last_review_id") long lastReviewId,
		@Param("start_date") LocalDateTime startDate,
		@Param("end_date") LocalDateTime endDate
	);

	//4-1. 날짜 설정한 경우(최신순 순, 무한 스크롤, 처음 이후)
	@Query("select r "
		+ "from Review r join fetch r.member m "
		+ "where m.memberId = :member_id and r.reviewId < :last_review_id and r.createdAt between :start_date and :end_date "
		+ "order by r.reviewId desc "
		+ "limit 24")
	List<Review> getReviewsNewestAfter(
		@Param("member_id") long memberId,
		@Param("last_review_id") long lastReviewId,
		@Param("start_date") LocalDateTime startDate,
		@Param("end_date") LocalDateTime endDate
	);

	//4-2. 날짜 설정한 경우(최신순 순, 무한 스크롤, 처음)
	@Query("select r "
		+ "from Review r join fetch r.member m "
		+ "where m.memberId = :member_id and r.createdAt between :start_date and :end_date "
		+ "order by r.reviewId desc "
		+ "limit 24")
	List<Review> getReviewsNewest(
		@Param("member_id") long memberId,
		@Param("start_date") LocalDateTime startDate,
		@Param("end_date") LocalDateTime endDate
	);
}
