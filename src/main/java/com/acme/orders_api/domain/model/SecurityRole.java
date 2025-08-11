package com.acme.orders_api.domain.model;

public enum SecurityRole {
  ADMIN,
  OPS;

  public String asAuthority() {
    return "ROLE_" + name();
  }
}
