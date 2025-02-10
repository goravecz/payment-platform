package com.bank.paymentservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {

  @Id
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private BigDecimal balance;

  public Account() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  @Override
  public boolean equals(Object o)
  {
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    Account account = (Account) o;
    return Objects.equals(id, account.id) && Objects.equals(name, account.name)
        && Objects.equals(balance, account.balance);
  }

  @Override
  public int hashCode()
  {
    int result = Objects.hashCode(id);
    result = 31 * result + Objects.hashCode(name);
    result = 31 * result + Objects.hashCode(balance);
    return result;
  }
}
