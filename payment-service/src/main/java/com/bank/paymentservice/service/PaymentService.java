package com.bank.paymentservice.service;

import com.bank.paymentservice.dto.PaymentResponse;
import com.bank.paymentservice.error.DuplicateTransactionException;
import com.bank.paymentservice.mapper.PaymentResponseMapper;
import com.bank.paymentservice.model.Transaction;
import com.bank.paymentservice.model.TransactionStatus;
import com.bank.paymentservice.repository.TransactionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private static final Logger LOG = LoggerFactory.getLogger(PaymentService.class);

  private final PaymentResponseMapper paymentResponseMapper;
  private final TransactionsRepository transactionsRepository;

  public PaymentService(PaymentResponseMapper paymentResponseMapper,
      TransactionsRepository transactionsRepository) {
    this.paymentResponseMapper = paymentResponseMapper;
    this.transactionsRepository = transactionsRepository;
  }

  public PaymentResponse processPayment(Transaction transaction) {
    LOG.info("Transaction: {}", transaction);

    if (transactionsRepository.existsById(transaction.getId())) {
      throw new DuplicateTransactionException(transaction.getId());
    }

    transaction.setStatus(TransactionStatus.PENDING);

    LOG.info("Saving transaction: {}", transaction);
    transactionsRepository.save(transaction);

    return paymentResponseMapper.toResponse(transaction);
  }

}
