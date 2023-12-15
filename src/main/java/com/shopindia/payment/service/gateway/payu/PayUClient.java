package com.shopindia.payment.service.gateway.payu;

import com.shopindia.payment.service.PaymentException;
import com.shopindia.payment.model.request.PaymentOrderCreateRequest;
import com.shopindia.payment.model.response.PaymentResponse;
import com.shopindia.payment.model.request.PaymentVerifyRequest;
import com.shopindia.payment.service.gateway.IPaymentClient;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@NoArgsConstructor
@Component
public class PayUClient implements IPaymentClient {
    @Value("${payUClientId}")
    private String clientId;

    @Value("${payUClientSecret}")
    private String clientSecret;

//    @Value("${payUCreateOrderUrl}")
//    private String createOrderUrl;


    //    public PayUClient(String clientId, String clientSecret) {
//        this.clientId = clientId;
//        this.clientSecret = clientSecret;
//    }

    @Override
    public PaymentResponse initiatePayment(PaymentOrderCreateRequest paymentOrderCreateRequest) throws PaymentException {
//    public PaymentResponse initiatePayment(Map<String, String> params) throws PaymentException {
        // Initialize the PaymentResponse object
        PaymentResponse response = new PaymentResponse();

        try {
            // Set the API endpoint and API key
            String endpoint = "https://test.cashfree.com/api/v2/cftoken/order";
            String apiKey = clientId + ":" + clientSecret;

            // Set the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("ContentType", MediaType.APPLICATION_FORM_URLENCODED.toString());
            headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(apiKey.getBytes()));
            headers.set("X-Client-Id", clientId);
            headers.set("X-Client-Secret", clientSecret);

            // Set the request body
            HttpEntity<PaymentOrderCreateRequest> requestEntity = new HttpEntity<>(paymentOrderCreateRequest, headers);
//            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(convertToMultiValueMap(params));

            // Send the request and get the response
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> resp = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, String.class);

            // Parse the response
            JSONObject json = new JSONObject(resp.getBody());

            headers.set("cftoken", json.getString("cftoken"));
            ResponseEntity<String> resp1 = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, String.class);

            String paymentUrl = json.getString("paymentUrl");

            // Set the response fields
            response.setSuccess(true);
            response.setTransactionId(json.getString("txId"));
            response.setOrderId(json.getString("orderId"));
            response.setOrderAmount(json.getString("orderAmount"));
            response.setOrderCurrency(json.getString("orderCurrency"));
            response.setPaymentMode(json.getString("paymentMode"));
            response.setPaymentStatus(json.getString("paymentStatus"));

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
        return null;
    }



}
