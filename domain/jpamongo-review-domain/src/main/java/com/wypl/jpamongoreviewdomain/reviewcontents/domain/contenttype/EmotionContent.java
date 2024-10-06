package com.wypl.jpamongoreviewdomain.reviewcontents.domain.contenttype;

import static com.wypl.jpamongoreviewdomain.reviewcontents.domain.BlockType.*;

import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContent;

public class EmotionContent extends ReviewContent {
	private final String emoji;
	private final String description;

	public EmotionContent(String emoji, String description) {
		super(REVIEW_EMOTION);
		this.emoji = emoji;
		this.description = description;
	}
}
