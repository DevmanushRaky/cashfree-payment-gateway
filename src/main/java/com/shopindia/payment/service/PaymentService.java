package com.shopindia.payment.service;

import com.shopindia.payment.service.gateway.IPaymentClient;
import com.shopindia.payment.utils.Common;
import com.shopindia.payment.model.request.PaymentOrderCreateRequest;
import com.shopindia.payment.model.response.PaymentResponse;
import com.shopindia.payment.model.request.PaymentVerifyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {


    @Autowired
    @Qualifier("cashFreeClient")
    IPaymentClient paymentClient;

    public PaymentResponse processPayment(PaymentOrderCreateRequest request) throws PaymentException {

        // Initiate the payment
        request.setOrderId(Common.getShortUID());
        request.setOrderCurrency(request.getOrderCurrency().toUpperCase());
        request.getCustomerDetails().setCustomerId(request.getCustomerDetails().getCustomerName());
        PaymentResponse response = paymentClient.initiatePayment(request);
        // Handle the response
        if (response.isSuccess()) {
            // Payment was successful, return the response to the caller
            System.out.println("Order payment initiated");
            return response;
        } else {
            // Payment failed, throw an exception
            System.out.println("failed to payment");
            throw new PaymentException(response.getErrorMessage(), response);
        }
    }

    public PaymentResponse verify(PaymentVerifyRequest request) throws PaymentException {

        // Initiate the payment
        PaymentResponse response = paymentClient.verifyPayment(request);
        // Handle the response
        if (response.isSuccess()) {
            // Payment was successful, return the response to the caller
            System.out.println("Order payment verified");
            return response;
        } else {
            // Payment failed, throw an exception
            System.out.println("failed to payment");
            throw new PaymentException(response.getErrorMessage(), response);
        }
    }
}

