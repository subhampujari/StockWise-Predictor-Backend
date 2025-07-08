package com.cerebromarkets.Service;

import com.cerebromarkets.modal.PaymentDetails;
import com.cerebromarkets.modal.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber,String accountHolderName,String ifsc,String bankName,User user);

    public PaymentDetails getUserPaymentDetails(User user);

}
