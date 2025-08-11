package com.acme.orders_api.infrastructure.idempotency;

import java.util.Map;

public class StoredResponse {
  private int httpStatus;
  private String responseBody;
  private Map<String, String> headers;

  public StoredResponse(Integer httpStatus, String responseBody) {
    this.httpStatus = httpStatus;
    this.responseBody = responseBody;
  }
}
