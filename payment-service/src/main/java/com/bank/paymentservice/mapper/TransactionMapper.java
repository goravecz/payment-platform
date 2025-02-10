package com.bank.paymentservice.mapper;

import com.bank.paymentservice.dto.Notification;
import com.bank.paymentservice.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
  public Notification toNotification(Transaction transaction) {
    return new Notification(
        transaction.getUuid(),
        transaction.getSenderId(),
        transaction.getReceiverId(),
        transaction.getAmount()
    );
  }
}
