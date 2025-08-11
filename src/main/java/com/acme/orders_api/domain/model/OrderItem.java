package com.acme.orders_api.domain.model;

import java.util.UUID;

public record OrderItem(UUID productId, int qty, long unitPrice){
  public long lineTotal(){ return unitPrice * qty; }
}
