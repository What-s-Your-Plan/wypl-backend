package com.wypl.jpamongoreviewdomain.reviewcontents.domain.contenttype;

import static com.wypl.jpamongoreviewdomain.reviewcontents.domain.BlockType.*;

import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContent;

public class WeatherContent extends ReviewContent {
	private String weather;
	private String description;

	public WeatherContent(String weather, String description) {
		super(REVIEW_WEATHER);
		this.weather = weather;
		this.description = description;
	}
}
