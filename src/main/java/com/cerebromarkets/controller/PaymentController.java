package com.cerebromarkets.controller;

import com.cerebromarkets.Service.PaymentService;
import com.cerebromarkets.Service.PaymentServiceImpl;
import com.cerebromarkets.Service.UserService;
import com.cerebromarkets.domain.PaymentMethod;
import com.cerebromarkets.modal.PaymentOrder;
import com.cerebromarkets.modal.User;
import com.cerebromarkets.response.PaymentResponse;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception,
            RazorpayException,
            StripeException { // StripeException needs to be imported or handled if it's from a specific library
        User user = userService.findUserProfileByJwt(jwt);

        PaymentResponse paymentResponse;

        PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);

        if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
            paymentResponse = paymentService.createRazorpayPaymentLing(user, amount,order.getId());
        } else {
            paymentResponse = paymentService.createStripePaymentLing(user, amount, order.getId()); // Assuming orderId is needed for Stripe
        }
        // The rest of the method is not visible in the image, so I'm stopping here.
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }
}
