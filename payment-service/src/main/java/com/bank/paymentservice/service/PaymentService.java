package com.bank.paymentservice.service;

import com.bank.paymentservice.dto.PaymentResponse;
import com.bank.paymentservice.mapper.PaymentResponseMapper;
import com.bank.paymentservice.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private static final Logger LOG = LoggerFactory.getLogger(PaymentService.class);

  private final PaymentResponseMapper paymentResponseMapper;

  public PaymentService(PaymentResponseMapper paymentResponseMapper) {
    this.paymentResponseMapper = paymentResponseMapper;
  }

  public PaymentResponse processPayment(Transaction transaction) {
    LOG.info("Transaction: {}", transaction);
    return paymentResponseMapper.toResponse(transaction);
  }

}
