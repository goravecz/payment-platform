package com.bank.paymentservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(

  @NotNull(message = "Unique key is required")
  UUID id,

  @NotBlank(message = "Sender id is required")
  String senderId,

  @NotBlank(message = "Receiver id is required")
  String receiverId,

  @NotNull(message = "Amount is required")
  @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
  BigDecimal amount
) {}
