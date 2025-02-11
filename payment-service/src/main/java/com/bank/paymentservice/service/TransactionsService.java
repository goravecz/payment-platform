package com.bank.paymentservice.service;

import com.bank.paymentservice.model.Transaction;
import com.bank.paymentservice.repository.TransactionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionsService {

  private static final Logger LOG = LoggerFactory.getLogger(TransactionsService.class);

  private final TransactionsRepository transactionsRepository;

  public TransactionsService(TransactionsRepository transactionsRepository) {
    this.transactionsRepository = transactionsRepository;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public Transaction saveWithLocking(Transaction transaction) {
    // just to acquire lock
    transactionsRepository.findByUuid(transaction.getUuid());

    // relying on the database to enforce uniqueness
    LOG.info("Saving transaction. Transaction: {}", transaction);
    return transactionsRepository.save(transaction);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public Transaction updateWithLocking(Transaction transaction) {
    Transaction savedTransaction = transactionsRepository.findByUuid(transaction.getUuid());

    if (savedTransaction == null) {
      LOG.error("Transaction not found. Transaction: {}", transaction);
      throw new RuntimeException("Transaction not found");
    }

    LOG.info("Updating transaction. Transaction: {}", transaction);
    return transactionsRepository.save(transaction);
  }
}
