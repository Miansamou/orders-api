package com.acme.orders_api.application;

import com.acme.orders_api.domain.model.Order;
import com.acme.orders_api.domain.model.OrderItem;
import com.acme.orders_api.domain.service.ProductReader;
import com.acme.orders_api.domain.repository.OrderRepository;
import com.acme.orders_api.domain.service.DiscountPolicy;
import com.acme.orders_api.domain.service.FreightCalculator;
import com.acme.orders_api.interfaces.rest.orders.CreateOrderReq;
import com.acme.orders_api.interfaces.rest.orders.OrderRes;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PlaceOrder {
  private final OrderRepository repo;
  private final FreightCalculator freight;
  private final DiscountPolicy discount;
  private final ProductReader products;

  public PlaceOrder(OrderRepository repo, FreightCalculator freight, DiscountPolicy discount, ProductReader products) {
    this.repo = repo;
    this.freight = freight;
    this.discount = discount;
    this.products = products;
  }

  public OrderRes place(CreateOrderReq req) {
    var items = req.items().stream().map(i -> {
      var prod = products.get(i.productId());
      return new OrderItem(prod.id(), i.quantity(), prod.priceCents());
    }).toList();

    long f = freight.calc(req.customerId(), items);
    long d = discount.calc(req.customerId(), items);

    var order = Order.create(UUID.randomUUID(), req.customerId(), items, f, d);
    var saved = repo.save(order);

    var itemRes = saved.items().stream()
      .map(it -> new OrderRes.ItemRes(it.productId(), it.qty(), it.unitPrice(), it.lineTotal()))
      .toList();

    return new OrderRes(
      saved.id(),
      saved.customerId(),
      saved.status(),
      saved.subtotal(),
      saved.discount(),
      saved.freight(),
      saved.total(),
      Instant.now(),
      itemRes
    );
  }

  public org.springframework.data.domain.Page<OrderRes> search(
    java.util.UUID customerId, String status, org.springframework.data.domain.Pageable pageable) {

    var st = (status != null && !status.isBlank())
      ? com.acme.orders_api.domain.model.OrderStatus.valueOf(status)
      : null;

    var filter = new com.acme.orders_api.domain.repository.OrderFilter(
      customerId, st, null, null
    );

    var page = repo.search(filter, pageable);
    return page.map(o -> new com.acme.orders_api.interfaces.rest.orders.OrderRes(
      o.id(),
      o.customerId(),
      o.status(),
      o.subtotal(),
      o.discount(),
      o.freight(),
      o.total(),
      Instant.now(),
      o.items().stream()
        .map(i -> new com.acme.orders_api.interfaces.rest.orders.OrderRes.ItemRes(
          i.productId(), i.qty(), i.unitPrice(), i.lineTotal()))
        .toList()
    ));
  }
}
