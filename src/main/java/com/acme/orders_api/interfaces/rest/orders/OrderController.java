package com.acme.orders_api.interfaces.rest.orders;

import com.acme.orders_api.application.PlaceOrder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
  private final PlaceOrder useCase;

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN','OPS')")
  ResponseEntity<OrderRes> create(@Valid @RequestBody CreateOrderReq req){
    var res = useCase.place(req);
    return ResponseEntity.status(HttpStatus.CREATED).body(res);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN','OPS')")
  Page<OrderRes> list(@PageableDefault(size=20,sort="createdAt",direction = Sort.Direction.DESC) Pageable p,
                      @RequestParam(required=false) UUID customerId,
                      @RequestParam(required=false) String status){
    return useCase.search(customerId,status,p);
  }
}
