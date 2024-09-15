package com.wypl.jpamongoreviewdomain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wypl.jpamongoreviewdomain.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
