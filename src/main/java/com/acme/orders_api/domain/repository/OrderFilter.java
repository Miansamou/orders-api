package com.acme.orders_api.domain.repository;

import com.acme.orders_api.domain.model.OrderStatus;

import java.time.LocalDate;
import java.util.UUID;

public record OrderFilter(
  UUID customerId,
  OrderStatus status,
  LocalDate createdAfter,
  LocalDate createdBefore
) {}
