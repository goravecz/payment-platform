package com.bank.paymentservice.error;

public class ValidationError {

  private String field;
  private Object rejectedValue;
  private String message;

  public ValidationError(String field, Object rejectedValue, String message) {
    this.field = field;
    this.rejectedValue = rejectedValue;
    this.message = message;
  }

  public String getField() {
    return field;
  }

  public String getMessage() {
    return message;
  }

  public Object getRejectedValue() {
      return rejectedValue;
  }

  @Override
  public String toString()
  {
    return "ValidationError{" +
        "field='" + field + '\'' +
        ", rejectedValue=" + rejectedValue +
        ", message='" + message + '\'' +
        '}';
  }
}
