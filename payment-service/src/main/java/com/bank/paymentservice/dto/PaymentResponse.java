package com.bank.paymentservice.dto;

public record PaymentResponse(
  String transactionId,
  String status,
  String failureReason,
  String message
) {}
