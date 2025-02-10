package com.bank.paymentservice.repository;

import com.bank.paymentservice.model.Transaction;
import jakarta.persistence.LockModeType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, UUID> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Transaction save(Transaction transaction);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  boolean existsById(UUID id);
}
