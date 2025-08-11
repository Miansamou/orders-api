package com.acme.orders_api.infrastructure.persistence.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

  @CreatedDate
  @Column(name="created_at", nullable=false, updatable=false)
  protected Instant createdAt;

  @CreatedBy
  @Column(name="created_by", updatable=false, length=100)
  protected String createdBy;

  @LastModifiedDate
  @Column(name="updated_at")
  protected Instant updatedAt;

  @LastModifiedBy
  @Column(name="updated_by", length=100)
  protected String updatedBy;
}
