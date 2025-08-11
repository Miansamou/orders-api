package com.acme.orders_api.domain.service;

import java.util.UUID;

public interface ProductReader {
  ProductData get(UUID productId);

  record ProductData(UUID id, long priceCents) {}
}
