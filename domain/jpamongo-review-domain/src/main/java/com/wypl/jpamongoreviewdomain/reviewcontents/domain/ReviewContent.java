package com.wypl.jpamongoreviewdomain.reviewcontents.domain;

public abstract class ReviewContent {
	private BlockType blockType;
	protected ReviewContent(BlockType blockType) {
		this.blockType = blockType;
	}
}
