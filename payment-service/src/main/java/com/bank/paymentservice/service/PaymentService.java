package com.bank.paymentservice.service;

import com.bank.paymentservice.dto.PaymentResponse;
import com.bank.paymentservice.mapper.PaymentResponseMapper;
import com.bank.paymentservice.model.Transaction;
import com.bank.paymentservice.model.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private static final Logger LOG = LoggerFactory.getLogger(PaymentService.class);

  private final PaymentResponseMapper paymentResponseMapper;
  private final TransactionsService transactionsService;
  private final AccountsService accountsService;

  public PaymentService(PaymentResponseMapper paymentResponseMapper,
      TransactionsService transactionsService,
      AccountsService accountsService) {
    this.paymentResponseMapper = paymentResponseMapper;
    this.transactionsService = transactionsService;
    this.accountsService = accountsService;
  }

  public PaymentResponse processPayment(Transaction transaction) {
    LOG.info("Transaction: {}", transaction);

    transaction.setStatus(TransactionStatus.PENDING);
    Transaction savedTransaction = transactionsService.save(transaction);
    Transaction processedTransaction = accountsService.makePayment(savedTransaction);

    return paymentResponseMapper.toResponse(processedTransaction);
  }

}
