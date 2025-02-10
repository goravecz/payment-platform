package com.bank.paymentservice.dto;

import com.bank.paymentservice.model.TransactionStatus;
import java.util.UUID;

public record PaymentResponse(
  UUID transactionId,
  TransactionStatus status
) {}
