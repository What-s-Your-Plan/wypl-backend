package com.wypl.wyplcore.review.data.request;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContent;

public record ReviewUpdateRequest(
	String title,

	@JsonProperty("schedule_id")
	int scheduleId,

	List<Map<String, ReviewContent>> contents
) {
}
