package com.acme.orders_api.infrastructure.idempotency;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.Objects;
import java.util.Optional;

@Service
public class IdempotencyService {
  private final IdempotencyKeyRepository repo;
  private final IdempotencyProperties props;

  public IdempotencyService(IdempotencyKeyRepository repo, IdempotencyProperties props) {
    this.repo = repo;
    this.props = props;
  }

  @Transactional(readOnly = true)
  public Optional<StoredResponse> tryGet(String key, ContentCachingRequestWrapper req) {
    return repo.findById(key).flatMap(e -> {
      if (Instant.now().isAfter(e.getExpiresAt())) return Optional.empty();
      String hash = hash(req);
      if (!hash.equals(e.getRequestHash())) return Optional.empty();
      return Optional.of(new StoredResponse(e.getHttpStatus(), e.getResponseBody()));
    });
  }

  @Transactional
  public StoredResponse storeOrGet(String key, ContentCachingRequestWrapper req, ContentCachingResponseWrapper res) {
    final int status = res.getStatus();
    final String body = new String(res.getContentAsByteArray(), StandardCharsets.UTF_8);
    final String reqHash = hash(req);
    final Instant now = Instant.now();

    var entity = new IdempotencyKeyEntity();
    entity.setKey(key);
    entity.setRequestHash(reqHash);
    if (status < 500) {
      entity.setHttpStatus(status);
      entity.setResponseBody(body);
    }
    entity.setCreatedAt(now);
    entity.setExpiresAt(now.plus(props.getTtl()));

    try {
      repo.save(entity);
      if (entity.getHttpStatus() != null) return new StoredResponse(status, body);
    } catch (org.springframework.dao.DataIntegrityViolationException dup) {
      var existing = repo.findById(key).orElseThrow();
      if (!reqHash.equals(existing.getRequestHash())) {
        return new StoredResponse(409, "{\"title\":\"Idempotency key conflict\"}");
      }
      return new StoredResponse(
        existing.getHttpStatus() == null ? status : existing.getHttpStatus(),
        existing.getResponseBody() == null ? body : existing.getResponseBody());
    }
    return new StoredResponse(status, body);
  }

  private static String hash(ContentCachingRequestWrapper req) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(req.getMethod().getBytes(StandardCharsets.UTF_8));
      md.update((byte) ':');
      md.update(req.getRequestURI().getBytes(StandardCharsets.UTF_8));
      md.update((byte) '?');
      md.update(Objects.toString(req.getQueryString(), "").getBytes(StandardCharsets.UTF_8));
      md.update((byte) '#');
      md.update(req.getContentAsByteArray());
      return HexFormat.of().formatHex(md.digest());
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }
}