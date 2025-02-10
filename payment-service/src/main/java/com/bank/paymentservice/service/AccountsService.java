package com.bank.paymentservice.service;

import com.bank.paymentservice.error.InsufficientFundsException;
import com.bank.paymentservice.error.ReceiverAccountNotFoundException;
import com.bank.paymentservice.error.SenderAccountNotFoundException;
import com.bank.paymentservice.model.Account;
import com.bank.paymentservice.model.Transaction;
import com.bank.paymentservice.model.TransactionStatus;
import com.bank.paymentservice.repository.AccountsRepository;
import com.bank.paymentservice.repository.TransactionsRepository;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountsService {

  private final AccountsRepository accountsRepository;
  private final TransactionsRepository transactionRepository;

    public AccountsService(AccountsRepository accountsRepository,
        TransactionsRepository transactionRepository) {
        this.accountsRepository = accountsRepository;
        this.transactionRepository = transactionRepository;
    }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public Transaction makePayment(Transaction transaction) {
    BigDecimal amount = transaction.getAmount();

    Account senderAccount = accountsRepository.findByUuid(transaction.getSenderId())
        .orElseThrow(() -> new SenderAccountNotFoundException(transaction.getUuid(), transaction.getSenderId()));

    Account receiverAccount = accountsRepository.findByUuid(transaction.getReceiverId())
        .orElseThrow(() -> new ReceiverAccountNotFoundException(transaction.getUuid(), transaction.getReceiverId()));

    if (senderAccount.getBalance().compareTo(amount) < 0) {
      transaction.setStatus(TransactionStatus.INSUFFICIENT_FUNDS);
      return transactionRepository.save(transaction);
    }

    senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
    receiverAccount.setBalance(receiverAccount.getBalance().add(amount));

    accountsRepository.save(senderAccount);
    accountsRepository.save(receiverAccount);

    transaction.setStatus(TransactionStatus.SUCCESS);
    return transactionRepository.save(transaction);
  }
}
