package com.bank.paymentservice.mapper;

import com.bank.paymentservice.dto.PaymentRequest;
import com.bank.paymentservice.model.FailureReason;
import com.bank.paymentservice.model.Transaction;
import com.bank.paymentservice.model.TransactionStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentRequestMapper {

  public Transaction toDomain(PaymentRequest request) {
    return new Transaction(
        request.id(),
        request.senderId(),
        request.receiverId(),
        request.amount(),
        TransactionStatus.NOT_SET,
        FailureReason.NOT_SET
    );
  }
}
