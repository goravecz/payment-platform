package com.bank.paymentservice.error;

import java.util.UUID;

public class ReceiverAccountNotFoundException extends RuntimeException {

  private final UUID id;
  private final UUID accountId;

  public ReceiverAccountNotFoundException(UUID id, UUID accountId) {
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
