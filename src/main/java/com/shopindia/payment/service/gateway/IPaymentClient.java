package com.shopindia.payment.service.gateway;

import com.shopindia.payment.service.PaymentException;
import com.shopindia.payment.model.request.PaymentOrderCreateRequest;
import com.shopindia.payment.model.response.PaymentResponse;
import com.shopindia.payment.model.request.PaymentVerifyRequest;

public interface IPaymentClient {
    PaymentResponse initiatePayment(PaymentOrderCreateRequest paymentOrderCreateRequest) throws PaymentException;

    PaymentResponse verifyPayment(PaymentVerifyRequest verifyRequest) throws PaymentException;


}
