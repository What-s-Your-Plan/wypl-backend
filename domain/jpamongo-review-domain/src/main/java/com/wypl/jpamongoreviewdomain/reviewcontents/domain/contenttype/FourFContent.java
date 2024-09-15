package com.wypl.jpamongoreviewdomain.reviewcontents.domain.contenttype;

import static com.wypl.jpamongoreviewdomain.reviewcontents.domain.BlockType.*;

import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContent;

public class FourFContent extends ReviewContent {
	private String facts;
	private String feeling;
	private String finding;
	private String future;

	public FourFContent(String facts, String feeling, String finding, String future) {
		super(REVIEW_4F);
		this.facts = facts;
		this.feeling = feeling;
		this.finding = finding;
		this.future = future;
	}
}
