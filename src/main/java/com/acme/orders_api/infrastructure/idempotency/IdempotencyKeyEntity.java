package com.acme.orders_api.infrastructure.idempotency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name="idempotency_keys")
public class IdempotencyKeyEntity {
  @Id
  @Column(length=80)
  private String key;

  @Column(name="request_hash", nullable=false, length=64)
  private String requestHash;

  @Column(name="response_body", columnDefinition="jsonb")
  private String responseBody;

  @Column(name="http_status")
  private Integer httpStatus;

  @Column(name="created_at", nullable=false)
  private Instant createdAt;

  @Column(name="expires_at", nullable=false)
  private Instant expiresAt;

}
