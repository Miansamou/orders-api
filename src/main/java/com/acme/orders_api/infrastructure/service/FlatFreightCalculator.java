package com.acme.orders_api.infrastructure.service;

import com.acme.orders_api.domain.model.OrderItem;
import com.acme.orders_api.domain.service.FreightCalculator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FlatFreightCalculator implements FreightCalculator {
  @Override
  public long calc(UUID customerId, List<OrderItem> items) {
    return 1500L;
  }
}
