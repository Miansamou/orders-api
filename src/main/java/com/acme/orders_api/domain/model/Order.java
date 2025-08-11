package com.acme.orders_api.domain.model;

import java.util.List;
import java.util.UUID;

public record Order(
  UUID id,
  UUID customerId,
  List<OrderItem> items,
  long subtotal,
  long discount,
  long freight,
  long total,
  OrderStatus status
) {
  public static Order create(UUID id, UUID customerId, List<OrderItem> items, long freight, long discount) {
    long subtotal = items.stream().mapToLong(OrderItem::lineTotal).sum();
    long total = subtotal + freight - discount;
    return new Order(id, customerId, items, subtotal, discount, freight, total, OrderStatus.NEW);
  }
}
