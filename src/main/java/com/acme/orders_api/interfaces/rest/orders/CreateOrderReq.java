package com.acme.orders_api.interfaces.rest.orders;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.UUID;

public record CreateOrderReq(
  @NotNull UUID customerId,
  @NotEmpty List<ItemReq> items,
  @PositiveOrZero long discountCents,
  @PositiveOrZero long freightCents
) {
  public record ItemReq(
    @NotNull UUID productId,
    @Positive int quantity,
    @Positive long unitPriceCents
  ) {}
}