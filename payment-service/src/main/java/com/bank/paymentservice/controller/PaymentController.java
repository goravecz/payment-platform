package com.bank.paymentservice.controller;

import com.bank.paymentservice.dto.PaymentRequest;
import com.bank.paymentservice.dto.PaymentResponse;
import com.bank.paymentservice.mapper.PaymentRequestMapper;
import com.bank.paymentservice.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {

  private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);

  private final PaymentService paymentService;
  private final PaymentRequestMapper paymentRequestMapper;

  public PaymentController(PaymentService paymentService,
      PaymentRequestMapper paymentRequestMapper) {
    this.paymentService = paymentService;
    this.paymentRequestMapper = paymentRequestMapper;
  }

  @PostMapping("/payment")
  public ResponseEntity<PaymentResponse> handlePaymentRequest(
      @Valid @RequestBody PaymentRequest request,
      HttpServletRequest httpRequest) {
    LOG.info("PaymentRequest: {}", request);
    httpRequest.setAttribute("id", request.id());
    PaymentResponse paymentResponse =
        paymentService.processPayment(paymentRequestMapper.toEntity(request));
    return ResponseEntity.accepted().body(paymentResponse);
  }
}
