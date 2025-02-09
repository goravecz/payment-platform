package com.bank.paymentservice.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Transaction {
  private UUID id;
  private String senderId;
  private String receiverId;
  private BigDecimal amount;
  private TransactionStatus status;
  private FailureReason failureReason;

  public Transaction() {
  }

  public Transaction(UUID id, String senderId, String receiverId, BigDecimal amount,
      TransactionStatus status, FailureReason failureReason) {
    this.id = id;
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.amount = amount;
    this.status = status;
    this.failureReason = failureReason;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }

  public String getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(String receiverId) {
    this.receiverId = receiverId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public TransactionStatus getStatus() {
    return status;
  }

  public void setStatus(TransactionStatus status) {
    this.status = status;
  }

  public FailureReason getFailureReason() {
    return failureReason;
  }

  public void setFailureReason(FailureReason failureReason) {
    this.failureReason = failureReason;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    Transaction that = (Transaction) o;
    return Objects.equals(id, that.id) && Objects.equals(senderId, that.senderId)
        && Objects.equals(receiverId, that.receiverId) && Objects.equals(amount,
        that.amount) && status == that.status && failureReason == that.failureReason;
  }

  @Override
  public int hashCode()
  {
    int result = Objects.hashCode(id);
    result = 31 * result + Objects.hashCode(senderId);
    result = 31 * result + Objects.hashCode(receiverId);
    result = 31 * result + Objects.hashCode(amount);
    result = 31 * result + Objects.hashCode(status);
    result = 31 * result + Objects.hashCode(failureReason);
    return result;
  }

  @Override
  public String toString()
  {
    return "Transaction{" +
        "id=" + id +
        ", senderId='" + senderId + '\'' +
        ", receiverId='" + receiverId + '\'' +
        ", amount=" + amount +
        ", status=" + status +
        ", failureReason=" + failureReason +
        '}';
  }
}
