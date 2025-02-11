package com.bank.paymentservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SenderReceiverValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SenderReceiverConstraint {
  String message() default "Sender and Receiver cannot be the same";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
