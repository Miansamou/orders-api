package com.acme.orders_api.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "order_items")
public class OrderItemEntity {

  @Id
  @Column(columnDefinition = "uuid")
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false, columnDefinition = "uuid")
  private OrderEntity order;

  @Column(name = "product_id", nullable = false, columnDefinition = "uuid")
  private UUID productId;

  @Column(nullable = false)
  private int quantity;

  @Column(name = "unit_price_cents", nullable = false)
  private long unitPriceCents;

  @Column(name = "line_total_cents", nullable = false)
  private long lineTotalCents;

}
