package com.wypl.jpamongoreviewdomain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wypl.jpamongoreviewdomain.review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
