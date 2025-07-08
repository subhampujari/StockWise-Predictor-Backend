package com.cerebromarkets.Service;

import com.cerebromarkets.domain.VerificationType;
import com.cerebromarkets.modal.ForgotPasswordToken;
import com.cerebromarkets.modal.User;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long Userid);

    void deleteToken(ForgotPasswordToken token);

}
