package com.cerebromarkets.Service;

import com.cerebromarkets.domain.PaymentMethod;
import com.cerebromarkets.modal.PaymentOrder;
import com.cerebromarkets.modal.User;
import com.cerebromarkets.response.PaymentResponse;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderByById(Long id) throws Exception;

    Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;

    PaymentResponse createRazorpayPaymentLing(User user, Long amount,Long orderId) throws RazorpayException;

    PaymentResponse createStripePaymentLing(User user, Long amount, Long orderId) throws StripeException;
}
