package com.acme.orders_api.infrastructure.persistence.mapper;

import com.acme.orders_api.domain.model.Order;
import com.acme.orders_api.domain.model.OrderItem;
import com.acme.orders_api.infrastructure.persistence.entity.OrderEntity;
import com.acme.orders_api.infrastructure.persistence.entity.OrderItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
  Order toDomain(OrderEntity e);
  OrderEntity toEntity(Order d);

  OrderItemEntity toEntity(OrderItem item);
  OrderItem toDomain(OrderItemEntity entity);
}
