package com.bank.paymentservice.service;

import com.bank.paymentservice.dto.PaymentResponse;
import com.bank.paymentservice.error.DuplicateTransactionException;
import com.bank.paymentservice.error.InsufficientFundsException;
import com.bank.paymentservice.mapper.PaymentResponseMapper;
import com.bank.paymentservice.mapper.TransactionMapper;
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
  private final TransactionMapper transactionMapper;
  private final TransactionsService transactionsService;
  private final AccountsService accountsService;
  private final NotificationService notificationService;

  public PaymentService(PaymentResponseMapper paymentResponseMapper,
      TransactionMapper transactionMapper,
      TransactionsService transactionsService,
      AccountsService accountsService,
      NotificationService notificationService) {
    this.paymentResponseMapper = paymentResponseMapper;
    this.transactionMapper = transactionMapper;
    this.transactionsService = transactionsService;
    this.accountsService = accountsService;
    this.notificationService = notificationService;
  }

  public PaymentResponse processPayment(Transaction transaction) {
    LOG.info("Transaction: {}", transaction);

    transaction.setStatus(TransactionStatus.PENDING);

    Transaction savedTransaction;
    try {
      savedTransaction = transactionsService.save(transaction);
    } catch (DataIntegrityViolationException ex) {
      throw new DuplicateTransactionException(transaction.getUuid());
    }

    Transaction processedTransaction = accountsService.makePayment(savedTransaction);

    if (processedTransaction.getStatus() == TransactionStatus.INSUFFICIENT_FUNDS) {
      throw new InsufficientFundsException(processedTransaction.getUuid(),
          processedTransaction.getSenderId());
    }

    notificationService.sendNotification(transactionMapper.toNotification(processedTransaction));

    return paymentResponseMapper.toResponse(processedTransaction);
  }
}
