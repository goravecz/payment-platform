package com.bank.paymentservice.service;

import com.bank.paymentservice.error.ReceiverAccountNotFoundException;
import com.bank.paymentservice.error.SenderAccountNotFoundException;
import com.bank.paymentservice.model.Account;
import com.bank.paymentservice.model.Transaction;
import com.bank.paymentservice.model.TransactionStatus;
import com.bank.paymentservice.repository.AccountsRepository;
import com.bank.paymentservice.repository.TransactionsRepository;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountsService {

  private static Logger LOG = LoggerFactory.getLogger(AccountsService.class);

  private final AccountsRepository accountsRepository;
  private final TransactionsRepository transactionRepository;

  public AccountsService(AccountsRepository accountsRepository,
      TransactionsRepository transactionRepository) {
    this.accountsRepository = accountsRepository;
    this.transactionRepository = transactionRepository;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public Transaction makePayment(Transaction transaction) {
    LOG.info("Making the payment. Transaction: {}", transaction);

    Account senderAccount = accountsRepository.findByUuid(transaction.getSenderId())
        .orElseThrow(() -> new SenderAccountNotFoundException(transaction.getUuid(),
            transaction.getSenderId()));
    Account receiverAccount = accountsRepository.findByUuid(transaction.getReceiverId())
        .orElseThrow(() -> new ReceiverAccountNotFoundException(transaction.getUuid(),
            transaction.getReceiverId()));

    BigDecimal amount = transaction.getAmount();

    if (senderAccount.getBalance().compareTo(amount) < 0) {
      transaction.setStatus(TransactionStatus.INSUFFICIENT_FUNDS);
      LOG.info("Updating transaction status in DB. Transaction: {}", transaction);
      return transactionRepository.save(transaction);
    }

    senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
    receiverAccount.setBalance(receiverAccount.getBalance().add(amount));

    LOG.info("Updating sender account in DB. Transaction: {}", transaction);
    accountsRepository.save(senderAccount);

    LOG.info("Updating receiver account in DB. Transaction: {}", transaction);
    accountsRepository.save(receiverAccount);

    transaction.setStatus(TransactionStatus.SUCCESS);

    LOG.info("Updating transaction status in DB. Transaction: {}", transaction);
    return transactionRepository.save(transaction);
  }
}
