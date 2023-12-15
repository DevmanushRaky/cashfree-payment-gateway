package com.shopindia.payment.model.response;

import lombok.Data;

@Data
public class PaymentResponse {
    private boolean success;
    private String errorMessage;
    private String transactionId;
    private String orderId;
    private String orderAmount;
    private String orderCurrency;
    private String paymentMode;
    private String paymentStatus;

    private String paymentSessionId;
    private Integer cfOrderId;
    private String orderStatus;


}
