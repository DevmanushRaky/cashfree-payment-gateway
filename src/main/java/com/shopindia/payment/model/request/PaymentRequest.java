package com.shopindia.payment.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentRequest {
    private String orderId;
    private Double orderAmount;
    private String orderCurrency;
    private String orderNote;
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
}
