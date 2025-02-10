package com.bank.paymentservice.repository;

import com.bank.paymentservice.model.Transaction;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Transaction save(Transaction transaction);

}
