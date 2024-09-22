package com.wypl.jpamongoreviewdomain.reviewcontents.domain;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import com.wypl.mongocommon.MongoBaseEntity;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "review_contents")
public class ReviewContents extends MongoBaseEntity {
	@Id
	private Long reviewId;

	// Todo : max size = 100
	private List<Map<String, ReviewContent>> contents;

	public static ReviewContents of(Long reviewId, List<Map<String, ReviewContent>> contents) {
		return new ReviewContents(reviewId, contents);
	}

	public void updateContents(List<Map<String, ReviewContent>> contents) {
		this.contents = contents;
	}
}
