package com.acme.orders_api.infrastructure.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt) {
    var roles = new HashSet<String>();

    Object direct = jwt.getClaim("roles");
    if (direct instanceof Collection<?> c1) {
      c1.forEach(r -> roles.add(String.valueOf(r)));
    }

    Object auths = jwt.getClaim("authorities");
    if (auths instanceof Collection<?> c2) {
      c2.forEach(r -> roles.add(String.valueOf(r)));
    }

    Object realmAccess = jwt.getClaim("realm_access");
    if (realmAccess instanceof Map<?,?> m) {
      Object ra = m.get("roles");
      if (ra instanceof Collection<?> c3) c3.forEach(r -> roles.add(String.valueOf(r)));
    }

    return roles.stream()
      .filter(Objects::nonNull)
      .flatMap(r -> {
        String s = r.trim();
        if (s.isEmpty()) return Stream.empty();
        return Stream.of(new SimpleGrantedAuthority(s.startsWith("ROLE_") ? s : "ROLE_" + s));
      })
      .collect(Collectors.toList());
  }
}
