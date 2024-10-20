package com.wypl.jpamongoreviewdomain.reviewcontents.domain.contenttype;

import static com.wypl.jpamongoreviewdomain.reviewcontents.domain.BlockType.*;

import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContent;

public class FourFContent extends ReviewContent {
	private final String facts;
	private final String feeling;
	private final String finding;
	private final String future;

	public FourFContent(String facts, String feeling, String finding, String future) {
		super(REVIEW_4F);
		this.facts = facts;
		this.feeling = feeling;
		this.finding = finding;
		this.future = future;
	}
}
