package com.wypl.jpamongoreviewdomain.reviewcontents.domain.contenttype;

import static com.wypl.jpamongoreviewdomain.reviewcontents.domain.BlockType.*;

import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContent;

public class KPTContent extends ReviewContent {
	private String keepStr;
	private String problemStr;
	private String tryStr;

	public KPTContent(String keepStr, String problemStr, String tryStr) {
		super(REVIEW_KPT);
		this.keepStr = keepStr;
		this.problemStr = problemStr;
		this.tryStr = tryStr;
	}
}
