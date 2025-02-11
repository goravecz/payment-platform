package com.bank.paymentservice.service;

import com.bank.paymentservice.model.Transaction;
import com.bank.paymentservice.repository.TransactionsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionsService {

  private final TransactionsRepository transactionsRepository;

  public TransactionsService(TransactionsRepository transactionsRepository) {
    this.transactionsRepository = transactionsRepository;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public Transaction save(Transaction transaction) {
    return transactionsRepository.save(transaction);
  }
}
