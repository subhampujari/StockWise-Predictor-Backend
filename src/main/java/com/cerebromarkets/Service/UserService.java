package com.cerebromarkets.Service;

import com.cerebromarkets.domain.VerificationType;
import com.cerebromarkets.modal.User;
import org.springframework.stereotype.Service;


public interface UserService {

    public User findUserProfileByJwt(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public User findUserById(Long userId) throws Exception;
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo,User user);
    User updatePassword(User user,String newPassword);
}
