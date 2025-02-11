package com.bank.paymentservice.error;

import com.bank.paymentservice.dto.PaymentRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class PaymentErrorHandler {

  private final Logger LOG = LoggerFactory.getLogger(PaymentErrorHandler.class);

  @ExceptionHandler(DuplicateTransactionException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateTransactionException(
      DuplicateTransactionException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(ex.getId(), "Duplicate transactions are not allowed!", List.of());
    LOG.error("{}", errorResponse);
    return ResponseEntity
        .badRequest()
        .body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    UUID id = ex.getBindingResult().getTarget() instanceof PaymentRequest request
        ? request.id()
        : null;

    List<ValidationError> validationErrors = new ArrayList<>();

    for (ObjectError globalError : ex.getBindingResult().getAllErrors()) {
      validationErrors.add(
          new ValidationError(
              globalError instanceof FieldError ? ((FieldError) globalError).getField()
                  : globalError.getObjectName(),
              globalError instanceof FieldError ? ((FieldError) globalError).getRejectedValue()
                  : null,
              globalError.getDefaultMessage()));
    }

    ErrorResponse errorResponse =
        new ErrorResponse(id, "Field validation failed!", validationErrors);
    LOG.error("{}", errorResponse);

    return ResponseEntity
        .badRequest()
        .body(errorResponse);
  }

  @ExceptionHandler(InsufficientFundsException.class)
  public ResponseEntity<ErrorResponse> handleInsufficientFundsException(
      InsufficientFundsException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getId(),
        "Insufficient funds for account id: " + ex.getAccountId(), List.of());
    LOG.error("{}", errorResponse);

    return ResponseEntity
        .badRequest()
        .body(errorResponse);
  }

  @ExceptionHandler(SenderAccountNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleSenderAccountNotFoundException(
      SenderAccountNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getId(),
        "Sender account not found for account id: " + ex.getAccountId(), List.of());
    LOG.error("{}", errorResponse);

    return ResponseEntity
        .badRequest()
        .body(errorResponse);
  }

  @ExceptionHandler(ReceiverAccountNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleReceiverAccountNotFoundException(
      ReceiverAccountNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getId(),
        "Receiver account not found for account id: " + ex.getAccountId(), List.of());
    LOG.error("{}", errorResponse);

    return ResponseEntity
        .badRequest()
        .body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
    UUID id = (UUID) request.getAttribute("id", RequestAttributes.SCOPE_REQUEST);
    ErrorResponse errorResponse = new ErrorResponse(id, ex.getMessage(), List.of());
    LOG.error("{}", errorResponse);

    return ResponseEntity
        .internalServerError()
        .body(errorResponse);
  }
}
