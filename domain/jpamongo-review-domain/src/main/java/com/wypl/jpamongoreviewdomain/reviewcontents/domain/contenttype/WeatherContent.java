package com.wypl.jpamongoreviewdomain.reviewcontents.domain.contenttype;

import static com.wypl.jpamongoreviewdomain.reviewcontents.domain.BlockType.*;

import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContent;

public class WeatherContent extends ReviewContent {
	private final String weather;
	private final String description;

	public WeatherContent(String weather, String description) {
		super(REVIEW_WEATHER);
		this.weather = weather;
		this.description = description;
	}
}
