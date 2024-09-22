package com.wypl.jpamongoreviewdomain.reviewcontents.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContents;

@Repository
public interface ReviewContentsRepository extends MongoRepository<ReviewContents, Long> {
	ReviewContents findByReviewIdAndDeletedAtNull(long reviewId);
}
