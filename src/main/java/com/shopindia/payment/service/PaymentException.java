package com.shopindia.payment.service;

import com.shopindia.payment.model.response.PaymentResponse;

public class PaymentException extends Exception {
    private PaymentResponse response;

    public PaymentException(String message, PaymentResponse response) {
        super(message);
        this.response = response;
    }

    public PaymentResponse getResponse() {
        return response;
    }
}

