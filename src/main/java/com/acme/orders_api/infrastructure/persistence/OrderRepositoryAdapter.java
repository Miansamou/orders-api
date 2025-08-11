package com.acme.orders_api.infrastructure.persistence;

import com.acme.orders_api.domain.model.Order;
import com.acme.orders_api.domain.repository.OrderFilter;
import com.acme.orders_api.infrastructure.persistence.repository.OrderJpaRepository;
import com.acme.orders_api.domain.repository.OrderRepository;
import com.acme.orders_api.infrastructure.persistence.entity.OrderEntity;
import com.acme.orders_api.infrastructure.persistence.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {
  private final OrderJpaRepository jpa;
  private final OrderMapper mapper;

  @Override
  public Optional<Order> findById(UUID id) {
    return jpa.findById(id).map(mapper::toDomain);
  }

  @Override
  public Order save(Order order) {
    var e = mapper.toEntity(order);
    e.getItems().forEach(i -> i.setOrder(e)); // backref
    return mapper.toDomain(jpa.save(e));
  }

  @Override
  public Page<Order> search(OrderFilter f, Pageable p) {
    Specification<OrderEntity> spec = Specification.allOf();
    if (f.customerId() != null)
      spec = spec.and((r, q, cb) -> cb.equal(r.get("customerId"), f.customerId()));
    if (f.status() != null)
      spec = spec.and((r, q, cb) -> cb.equal(r.get("status"), f.status()));
    return jpa.findAll(spec, p).map(mapper::toDomain);
  }
}
