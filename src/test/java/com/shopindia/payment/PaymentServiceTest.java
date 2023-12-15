package com.shopindia.payment;

import com.shopindia.payment.model.request.CustomerDetails;
import com.shopindia.payment.model.request.PaymentOrderCreateRequest;
import com.shopindia.payment.model.response.PaymentResponse;
import com.shopindia.payment.service.PaymentException;
import com.shopindia.payment.service.PaymentService;
import com.shopindia.payment.service.gateway.IPaymentClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

        @Mock
        private IPaymentClient paymentClient;

        @InjectMocks
        private PaymentService paymentService;


        @Test
    public void testProcessPayment() throws PaymentException {
        // Set up the payment request
        PaymentOrderCreateRequest request = new PaymentOrderCreateRequest();
        request.setOrderId("123456");
        request.setOrderAmount(100.00);
        request.setOrderCurrency("INR");
        request.setOrderNote("Test payment");

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setCustomerName("John Smith");
        customerDetails.setCustomerEmail("john@example.com");
        customerDetails.setCustomerPhone("9876543210");

        request.setCustomerDetails(customerDetails);

            PaymentResponse expectedResponse = new PaymentResponse();
            expectedResponse.setSuccess(true);
            expectedResponse.setCfOrderId(123456);
            expectedResponse.setOrderStatus("ACTIVE");

            when(paymentClient.initiatePayment(request)).thenReturn(expectedResponse);
            // call the addVendor method
            PaymentResponse actualResponse = paymentService.processPayment( request);

            // verify the response
            assertNotNull(actualResponse);

    }
}