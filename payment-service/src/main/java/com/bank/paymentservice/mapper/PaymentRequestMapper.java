package com.bank.paymentservice.mapper;

import com.bank.paymentservice.dto.PaymentRequest;
import com.bank.paymentservice.model.Transaction;
import com.bank.paymentservice.model.TransactionStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentRequestMapper {

  public Transaction toEntity(PaymentRequest request) {
    return new Transaction(
        request.id(),
        request.senderId(),
        request.receiverId(),
        request.amount(),
        TransactionStatus.NOT_SET
    );
  }
}
