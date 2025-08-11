package com.acme.orders_api.infrastructure.persistence.entity;

import com.acme.orders_api.domain.model.OrderStatus;
import com.acme.orders_api.infrastructure.persistence.entity.base.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "orders")
public class OrderEntity extends Auditable {

  @Id
  @GeneratedValue
  @UuidGenerator
  private UUID id;

  @Column(name = "customer_id", nullable = false, columnDefinition = "uuid")
  private UUID customerId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private OrderStatus status;

  @Column(name = "subtotal_cents", nullable = false)
  private long subtotalCents;

  @Column(name = "discount_cents", nullable = false)
  private long discountCents;

  @Column(name = "freight_cents", nullable = false)
  private long freightCents;

  @Column(name = "total_cents", nullable = false)
  private long totalCents;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItemEntity> items = new ArrayList<>();

}