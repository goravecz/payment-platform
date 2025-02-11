package com.bank.paymentservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(

    @Schema(description = "The transaction's unique id", example = "123c1262-e29b-1213-a851-226614174012")
    @NotNull(message = "Unique key is required")
    UUID id,

    @Schema(description = "The sender's id", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull(message = "Sender id is required")
    UUID senderId,

    @Schema(description = "The receiver's id", example = "550e8400-e29b-41d4-a716-446655440001")
    @NotNull(message = "Receiver id is required")
    UUID receiverId,

    @Schema(description = "The amount to be paid", example = "100")
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    BigDecimal amount
) {

}
