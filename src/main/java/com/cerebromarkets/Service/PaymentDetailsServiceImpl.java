package com.cerebromarkets.Service;

import com.cerebromarkets.Repository.PaymentDetailsRepository;
import com.cerebromarkets.modal.PaymentDetails;
import com.cerebromarkets.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService{

     @Autowired
     private PaymentDetailsRepository paymentDetailsRepository;

    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc, String bankName, User user) {
       PaymentDetails paymentDetails=new PaymentDetails();
       paymentDetails.setAccountNumber(accountNumber);
       paymentDetails.setAccountHolderName(accountHolderName);
       paymentDetails.setUser(user);
       paymentDetails.setBankName(bankName);
       paymentDetails.setIfsc(ifsc);
        return paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUserPaymentDetails(User user) {
        return paymentDetailsRepository.findByUserId(user.getId());
    }
}
