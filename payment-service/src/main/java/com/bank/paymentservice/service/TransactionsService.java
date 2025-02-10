package com.bank.paymentservice.service;

import com.bank.paymentservice.error.DuplicateTransactionException;
import com.bank.paymentservice.model.Transaction;
import com.bank.paymentservice.repository.TransactionsRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {

  private final TransactionsRepository transactionsRepository;

  public TransactionsService(TransactionsRepository transactionsRepository) {
      this.transactionsRepository = transactionsRepository;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public Transaction save(Transaction transaction) {
    // The unique constraint on the (primary key) id should throw an DataIntegrityViolationException
    // if the transaction already exists, but it doesn't. So I have to check it manually.
    if (transactionsRepository.existsById(transaction.getId())) {
      throw new DuplicateTransactionException(transaction.getId());
    }
    return transactionsRepository.save(transaction);
  }
}
