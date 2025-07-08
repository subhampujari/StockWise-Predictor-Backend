package com.cerebromarkets.Service;

import com.cerebromarkets.Repository.ForgotPasswordRepository;
import com.cerebromarkets.domain.VerificationType;
import com.cerebromarkets.modal.ForgotPasswordToken;
import com.cerebromarkets.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Override
    public ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {
        ForgotPasswordToken token=new ForgotPasswordToken();
        token.setUser(user);
        token.setSendTo(sendTo);
        token.setVerificationType(verificationType);
        token.setOtp(otp);
        token.setId(id);
        return forgotPasswordRepository.save(token);
    }

    @Override
    public ForgotPasswordToken findById(String id) {
        Optional<ForgotPasswordToken> token=forgotPasswordRepository.findById(id);
        return token.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(Long userid) {
        return forgotPasswordRepository.findByUserId(userid);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {
          forgotPasswordRepository.delete(token);
    }
}
