package com.acme.orders_api.domain.repository;

import com.acme.orders_api.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
  Optional<Order> findById(UUID id);

  Order save(Order order);

  Page<Order> search(OrderFilter filter, Pageable pageable);
}
