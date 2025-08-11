package com.acme.orders_api.interfaces.rest.orders;

import com.acme.orders_api.domain.model.OrderStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderRes(
  UUID id,
  UUID customerId,
  OrderStatus status,
  long subtotalCents,
  long discountCents,
  long freightCents,
  long totalCents,
  Instant createdAt,
  List<ItemRes> items
) {
  public record ItemRes(
    UUID productId,
    int quantity,
    long unitPriceCents,
    long lineTotalCents
  ) {}
}
