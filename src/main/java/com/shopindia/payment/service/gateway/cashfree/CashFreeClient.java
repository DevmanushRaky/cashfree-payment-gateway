package com.shopindia.payment.service.gateway.cashfree;

import com.shopindia.payment.model.request.PaymentOrderCreateRequest;
import com.shopindia.payment.model.request.PaymentVerifyRequest;
import com.shopindia.payment.service.gateway.IPaymentClient;
import com.shopindia.payment.service.PaymentException;
import com.shopindia.payment.model.response.PaymentResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Component
public class CashFreeClient implements IPaymentClient {
    @Value("${cashFreeClientId}")
    private String clientId;

    @Value("${cashFreeClientSecret}")
    private String clientSecret;

    @Value("${cashFreeCreateOrderUrl}")
    private String createOrderUrl;


    public CashFreeClient() {

    }

    @Override
    public PaymentResponse initiatePayment(PaymentOrderCreateRequest paymentOrderCreateRequest) throws PaymentException {
//    public PaymentResponse initiatePayment(Map<String, String> params) throws PaymentException {
        // Initialize the PaymentResponse object
        PaymentResponse response = new PaymentResponse();

        try {
            // Set the API endpoint and API key
            String apiKey = clientId + ":" + clientSecret;

            // Set the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("ContentType", MediaType.APPLICATION_FORM_URLENCODED.toString());
            headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(apiKey.getBytes()));
            headers.set("x-api-version", "2022-09-01");
            headers.set("X-Client-Id", clientId);
            headers.set("X-Client-Secret", clientSecret);

            // Set the request body
            HttpEntity<PaymentOrderCreateRequest> requestEntity = new HttpEntity<>(paymentOrderCreateRequest, headers);
//            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(convertToMultiValueMap(params));

            // Send the request and get the response
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> resp = restTemplate.exchange(createOrderUrl, HttpMethod.POST, requestEntity, String.class);

            // Parse the response
            JSONObject json = new JSONObject(resp.getBody());

//            headers.set("cftoken", json.getString("cftoken"));
//            ResponseEntity<String> resp1 = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, String.class);

//            String paymentUrl = json.getString("paymentUrl");

            // Set the response fields
            response.setSuccess(true);
            response.setCfOrderId(json.getInt("cf_order_id"));
            response.setPaymentSessionId(json.getString("payment_session_id"));
            response.setOrderStatus(json.getString("order_status"));

//            response.setTransactionId(json.getString("txId"));
//            response.setOrderId(json.getString("orderId"));
//            response.setOrderAmount(json.getString("orderAmount"));
//            response.setOrderCurrency(json.getString("orderCurrency"));
//            response.setPaymentMode(json.getString("paymentMode"));
//            response.setPaymentStatus(json.getString("paymentStatus"));

//            // Redirect the user to the payment URL
//              return "redirect:" + paymentUrl;
        } catch (Exception e) {
            e.printStackTrace();
            // Set the error message
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            throw new PaymentException("Error initiating payment", response);

        }

        return response;
    }

    @Override
    public PaymentResponse verifyPayment(PaymentVerifyRequest paymentOrderCreateRequest) throws PaymentException {
        PaymentResponse response = new PaymentResponse();

        try {
            // Set the API endpoint and API key
            String apiKey = clientId + ":" + clientSecret;

            // Set the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("ContentType", MediaType.APPLICATION_FORM_URLENCODED.toString());
            headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(apiKey.getBytes()));
            headers.set("x-api-version", "2022-09-01");
            headers.set("X-Client-Id", clientId);
            headers.set("X-Client-Secret", clientSecret);

            // Set the request body
            HttpEntity requestEntity = new HttpEntity(headers);
//            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(convertToMultiValueMap(params));

            // Send the request and get the response
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> resp = restTemplate.exchange(createOrderUrl + "/" + paymentOrderCreateRequest.getOrderId(), HttpMethod.GET, requestEntity, String.class);

            // Parse the response
            JSONObject json = new JSONObject(resp.getBody());

            // Set the response fields
            response.setSuccess(true);
            response.setOrderStatus(json.getString("order_status"));

        } catch (Exception e) {
            e.printStackTrace();
            // Set the error message
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            throw new PaymentException("Error initiating payment", response);

        }

        return response;

    }
}




