package com.bank.paymentservice.error;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateTransactionException extends RuntimeException {

  private final UUID id;

  public DuplicateTransactionException(UUID id) {
    super();
    this.id = id;
  }

  public UUID getId() {
    return id;
  }
}
