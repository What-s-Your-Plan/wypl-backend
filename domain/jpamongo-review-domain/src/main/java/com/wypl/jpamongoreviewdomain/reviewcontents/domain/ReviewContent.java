package com.wypl.jpamongoreviewdomain.reviewcontents.domain;

import lombok.Getter;

@Getter
public abstract class ReviewContent {
	private BlockType blockType;
	protected ReviewContent(BlockType blockType) {
		this.blockType = blockType;
	}
}
