package com.wypl.jpacommon;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.wypl.common.exception.WyplException;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class JpaBaseEntity {

	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "modified_at", nullable = false)
	private LocalDateTime modifiedAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	public void delete() {
		if (isDeleted()) {
			throw new WyplException(JpaErrorCode.ALREADY_DELETED_ENTITY);
		}
		this.deletedAt = LocalDateTime.now();
	}

	public void restore() {
		if (isNotDeleted()) {
			throw new WyplException(JpaErrorCode.NON_DELETED_ENTITY);
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
