package com.wypl.jpamongoreviewdomain.reviewcontents.domain.contenttype;

import static com.wypl.jpamongoreviewdomain.reviewcontents.domain.BlockType.*;

import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContent;

public class TextContent extends ReviewContent {
	private final String text;
	public TextContent(String text) {
		super(REVIEW_TEXT);
		this.text = text;
	}
}
