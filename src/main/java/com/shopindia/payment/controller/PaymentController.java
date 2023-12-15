package com.shopindia.payment.controller;

import com.shopindia.payment.service.PaymentException;
import com.shopindia.payment.service.PaymentService;
import com.shopindia.payment.model.request.PaymentOrderCreateRequest;
import com.shopindia.payment.model.response.PaymentResponse;
import com.shopindia.payment.model.request.PaymentVerifyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value="/checkout", method = {RequestMethod.GET})
    public String openCheckout(ModelMap model) {
        PaymentOrderCreateRequest request = new PaymentOrderCreateRequest();
        model.addAttribute("request", request);
        return "checkoutPage";

    }

    @RequestMapping(value = "/checkout", method = {RequestMethod.POST})
    public String processCheckout(@ModelAttribute PaymentOrderCreateRequest request, ModelMap model) {
        try {
            PaymentResponse response = paymentService.processPayment(request);

            model.addAttribute("response", response);
            return "checkout-success";
        } catch (PaymentException e) {
            model.addAttribute("response", e.getResponse());
            return "checkout-error";
        }

    }

    @RequestMapping(value = "/verify", method = {RequestMethod.POST})
    public ResponseEntity verify(@RequestBody PaymentVerifyRequest request, ModelMap model) {
        try {
            PaymentResponse response = paymentService.verify(request);

            model.addAttribute("response", response);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (PaymentException e) {
            model.addAttribute("response", e.getResponse());
            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
        }

    }
}
