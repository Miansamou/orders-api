package com.acme.orders_api.infrastructure.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
public class CurrentUserAuditorAware implements AuditorAware<String> {
  public Optional<String> getCurrentAuditor() {
    var a = SecurityContextHolder.getContext().getAuthentication();
    return Optional.ofNullable(a).map(Principal::getName);
  }
}
