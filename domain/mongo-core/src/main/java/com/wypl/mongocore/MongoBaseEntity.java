package com.wypl.mongocore;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import com.wypl.common.exception.WyplException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MongoBaseEntity {
	@CreatedDate
	@Field(name = "created_at", write = Field.Write.NON_NULL)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Field(name = "modified_at", write = Field.Write.NON_NULL)
	private LocalDateTime modifiedAt;

	@Field(name = "deleted_at")
	private LocalDateTime deletedAt;

	public void delete() {
		if (isDeleted()) {
			throw new WyplException(MongoErrorCode.ALREADY_DELETED_ENTITY);
		}
		this.deletedAt = LocalDateTime.now();
	}

	public void restore() {
		if (isNotDeleted()) {
			throw new WyplException(MongoErrorCode.NON_DELETED_ENTITY);
		}
		this.deletedAt = null;
	}

	public boolean isNotDeleted() {
		return deletedAt == null;
	}

	public boolean isDeleted() {
		return !isNotDeleted();
	}
}
