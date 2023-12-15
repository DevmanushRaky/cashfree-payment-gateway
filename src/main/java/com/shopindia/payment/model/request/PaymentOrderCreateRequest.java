package com.shopindia.payment.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class PaymentOrderCreateRequest {
    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("order_amount")
    private Double orderAmount;

    @JsonProperty("order_currency")
    private String orderCurrency;

    @JsonProperty("order_note")
    private String orderNote;

    @JsonProperty("customer_details")
    private CustomerDetails customerDetails;


}
