package com.acme.orders_api.infrastructure.idempotency;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "idempotency")
public class IdempotencyProperties {
  private boolean enabled = true;
  private Duration ttl = Duration.ofHours(24);
}
