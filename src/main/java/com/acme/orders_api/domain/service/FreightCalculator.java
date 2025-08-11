package com.acme.orders_api.domain.service;

import com.acme.orders_api.domain.model.OrderItem;

import java.util.List;
import java.util.UUID;

public interface FreightCalculator {
  long calc(UUID customerId, List<OrderItem> items);
}
