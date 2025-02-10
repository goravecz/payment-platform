package com.bank.paymentservice.error;

import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {

  private final UUID id;
  private final UUID accountId;

  public InsufficientFundsException(UUID id, UUID accountId) {
    super();
    this.id = id;
    this.accountId = accountId;
  }

  public UUID getId() {
    return id;
  }

  public UUID getAccountId() {
    return accountId;
  }

}
