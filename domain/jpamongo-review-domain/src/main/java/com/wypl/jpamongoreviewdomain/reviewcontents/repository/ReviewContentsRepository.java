package com.wypl.jpamongoreviewdomain.reviewcontents.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContents;

public interface ReviewContentsRepository extends MongoRepository<ReviewContents, Long> {
}
