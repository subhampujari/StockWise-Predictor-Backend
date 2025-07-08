package com.cerebromarkets.Service;

import com.cerebromarkets.domain.VerificationType;
import com.cerebromarkets.modal.User;
import com.cerebromarkets.modal.VerificationCode;

public interface VerificationCodeService {
  public VerificationCode sendVerificationCode(User user , VerificationType verificationType);
    public  VerificationCode getVerificationCodeById(Long id) throws Exception;
    public  VerificationCode getVerificationCodeByUser(Long userId);
    public  void deleteVerificationCodeById(VerificationCode verificationCode);
}
