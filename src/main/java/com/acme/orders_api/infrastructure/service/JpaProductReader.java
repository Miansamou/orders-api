package com.acme.orders_api.infrastructure.service;

import com.acme.orders_api.domain.service.ProductReader;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JpaProductReader implements ProductReader {
  @Override
  public ProductData get(UUID productId) {
    return new ProductReader.ProductData(productId, 1000L);
  }
}
