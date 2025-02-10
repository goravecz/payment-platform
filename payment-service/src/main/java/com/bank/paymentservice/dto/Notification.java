package com.bank.paymentservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record Notification(
    UUID id,
    UUID senderId,
    UUID receiverId,
    BigDecimal amount
) {}
