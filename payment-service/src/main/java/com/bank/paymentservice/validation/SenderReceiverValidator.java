package com.bank.paymentservice.validation;

import com.bank.paymentservice.dto.PaymentRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SenderReceiverValidator implements ConstraintValidator<SenderReceiverConstraint, PaymentRequest> {

  @Override
  public void initialize(SenderReceiverConstraint constraintAnnotation) {
  }

  @Override
  public boolean isValid(PaymentRequest paymentRequest,
      ConstraintValidatorContext constraintValidatorContext) {
    return !paymentRequest.senderId().equals(paymentRequest.receiverId());
  }
}
