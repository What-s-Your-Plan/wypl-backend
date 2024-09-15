package com.wypl.jpamongoreviewdomain.reviewcontents.domain;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import com.wypl.mongocore.MongoBaseEntity;

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

	private List<Map<String, Object>> contents;
}
