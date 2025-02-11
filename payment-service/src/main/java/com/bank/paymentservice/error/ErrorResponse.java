package com.bank.paymentservice.error;

import java.util.List;
import java.util.UUID;

public record ErrorResponse(
    UUID id,
    String message,
    List<ValidationError> validationErrors
) {

}
