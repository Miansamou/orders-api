package com.acme.orders_api.infrastructure.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.net.URI;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProblemDetails(
  URI type,
  String title,
  int status,
  String detail,
  String instance,
  Instant timestamp
) {}
