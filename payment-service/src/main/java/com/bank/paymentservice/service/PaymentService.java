package com.bank.paymentservice.service;

import com.bank.paymentservice.dto.PaymentResponse;
import com.bank.paymentservice.error.DuplicateTransactionException;
import com.bank.paymentservice.mapper.PaymentResponseMapper;
import com.bank.paymentservice.model.Transaction;
import com.bank.paymentservice.model.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private static final Logger LOG = LoggerFactory.getLogger(PaymentService.class);

  private final PaymentResponseMapper paymentResponseMapper;
  private final TransactionsService transactionsService;

  public PaymentService(PaymentResponseMapper paymentResponseMapper,
      TransactionsService transactionsService) {
    this.paymentResponseMapper = paymentResponseMapper;
    this.transactionsService = transactionsService;
  }

  public PaymentResponse processPayment(Transaction transaction) {
    LOG.info("Transaction: {}", transaction);

    transaction.setStatus(TransactionStatus.PENDING);
    try {
      transaction = transactionsService.save(transaction);
    } catch (DataIntegrityViolationException ex) {
      LOG.error("Duplicate transaction exception: {}", ex.getMessage());
      throw new DuplicateTransactionException(transaction.getId());
    }

    return paymentResponseMapper.toResponse(transaction);
  }

}
