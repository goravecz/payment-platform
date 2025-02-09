package com.bank.paymentservice.repository;

import com.bank.paymentservice.model.Transaction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
  boolean existsById(UUID id);
}
