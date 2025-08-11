package com.acme.orders_api.infrastructure.persistence.repository;

import com.acme.orders_api.domain.model.OrderStatus;
import com.acme.orders_api.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID>, JpaSpecificationExecutor<OrderEntity> {

  @EntityGraph(attributePaths = "items")
  Optional<OrderEntity> findWithItemsById(UUID id);

  @Query("""
    select o from OrderEntity o
    left join fetch o.items i
    where o.id = :id
  """)
  Optional<OrderEntity> findByIdFetchItems(@Param("id") UUID id);

  interface OrderSummary {
    UUID getId();
    UUID getCustomerId();
    OrderStatus getStatus();
    long getTotalCents();
    Instant getCreatedAt();
  }

  Page<OrderSummary> findAllBy(Specification<OrderEntity> spec, Pageable pageable);
}
