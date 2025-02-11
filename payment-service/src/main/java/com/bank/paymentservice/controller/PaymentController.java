package com.bank.paymentservice.controller;

import com.bank.paymentservice.dto.PaymentRequest;
import com.bank.paymentservice.dto.PaymentResponse;
import com.bank.paymentservice.mapper.PaymentRequestMapper;
import com.bank.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Payments", description = "Operations related to payments")
public class PaymentController {

  private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);

  private final PaymentService paymentService;
  private final PaymentRequestMapper paymentRequestMapper;

  public PaymentController(PaymentService paymentService,
      PaymentRequestMapper paymentRequestMapper) {
    this.paymentService = paymentService;
    this.paymentRequestMapper = paymentRequestMapper;
  }

  @Operation(summary = "Request a payment", description = "Processes a payment request")
  @ApiResponses({
      @ApiResponse(responseCode = "202", description = "Payment request accepted and is in progress"),
      @ApiResponse(responseCode = "400", description = "Bad request. Possible reasons: field validation, duplicate transaction, insufficient funds"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
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
