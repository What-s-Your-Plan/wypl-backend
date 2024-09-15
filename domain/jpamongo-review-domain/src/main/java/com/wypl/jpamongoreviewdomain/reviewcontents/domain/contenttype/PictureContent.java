package com.wypl.jpamongoreviewdomain.reviewcontents.domain.contenttype;

import static com.wypl.jpamongoreviewdomain.reviewcontents.domain.BlockType.*;

import com.wypl.jpamongoreviewdomain.reviewcontents.domain.ReviewContent;

public class PictureContent extends ReviewContent {
	private String path;

	public PictureContent(String path) {
		super(REVIEW_PICTURE);
		this.path = path;
	}
}
