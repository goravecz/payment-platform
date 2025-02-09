package com.bank.paymentservice.mapper;

import com.bank.paymentservice.dto.PaymentResponse;
import com.bank.paymentservice.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class PaymentResponseMapper {
  public PaymentResponse toResponse(Transaction transaction) {
    return new PaymentResponse(
        transaction.getId(),
        transaction.getStatus(),
        transaction.getFailureReason(),
        ""
    );
  }
}
