package com.bank.paymentservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "transactions")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private UUID uuid;

  @Column(nullable = false)
  private UUID senderId;

  @Column(nullable = false)
  private UUID receiverId;

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionStatus status;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private java.time.LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private java.time.LocalDateTime updatedAt;

  public Transaction() {
  }

  public Transaction(UUID uuid, UUID senderId, UUID receiverId, BigDecimal amount,
      TransactionStatus status) {
    this.uuid = uuid;
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.amount = amount;
    this.status = status;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public UUID getSenderId() {
    return senderId;
  }

  public void setSenderId(UUID senderId) {
    this.senderId = senderId;
  }

  public UUID getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(UUID receiverId) {
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
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
        that.amount) && status == that.status;
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(id);
    result = 31 * result + Objects.hashCode(senderId);
    result = 31 * result + Objects.hashCode(receiverId);
    result = 31 * result + Objects.hashCode(amount);
    result = 31 * result + Objects.hashCode(status);
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
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        '}';
  }
}
