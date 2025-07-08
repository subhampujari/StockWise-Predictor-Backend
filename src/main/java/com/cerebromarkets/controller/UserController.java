package com.cerebromarkets.controller;


import com.cerebromarkets.request.ForgotPasswordTokenRequest;
import com.cerebromarkets.Service.EmailService;
import com.cerebromarkets.Service.ForgotPasswordService;
import com.cerebromarkets.Service.UserService;
import com.cerebromarkets.Service.VerificationCodeService;
import com.cerebromarkets.domain.VerificationType;
import com.cerebromarkets.modal.ForgotPasswordToken;
import com.cerebromarkets.modal.User;
import com.cerebromarkets.modal.VerificationCode;
import com.cerebromarkets.request.ResetPasswordRequest;
import com.cerebromarkets.response.ApiResponse;
import com.cerebromarkets.response.AuthResponse;
import com.cerebromarkets.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private EmailService emailService;
    private String jwt;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        return  new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@PathVariable String otp,@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUser(user.getId());
        String sendTo=verificationCode.getVerificationType().equals(VerificationType.EMAIL)?verificationCode.getEmail():verificationCode.getMobile();
         boolean isVerified=verificationCode.getOtp().equals(otp);
         if(isVerified){
             User updatedUser=userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(),sendTo,user);
             verificationCodeService.deleteVerificationCodeById(verificationCode);
             return  new ResponseEntity<>(updatedUser,HttpStatus.OK);
         }
        throw new Exception("Wrong Otp you typed ..");
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwt, @PathVariable VerificationType verificationType) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUser(user.getId());

        if(verificationCode == null){
            verificationCode=verificationCodeService.sendVerificationCode(user,verificationType);
        }
           if(verificationType.equals(VerificationType.EMAIL)){
               emailService.sendVerificationOtpEmail(user.getEmail(),verificationCode.getOtp());
           }
        return  new ResponseEntity<>("Verification Otp Send Successfully ", HttpStatus.OK);
    }

    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp( @RequestBody ForgotPasswordTokenRequest req) throws Exception {
        User user=userService.findUserByEmail(req.getSendTo());
        String otp= OtpUtils.generateOtp();
        UUID uuid=UUID.randomUUID();
        String id=uuid.toString();
        ForgotPasswordToken token=forgotPasswordService.findByUser(user.getId());
        if(token == null){
            token=forgotPasswordService.createToken(user,id,otp,req.getVerificationType(),req.getSendTo());
        }

        if(req.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(user.getEmail(),token.getOtp());
        }
        AuthResponse response=new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Password reset otp sent successfully.. ");
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String id, @RequestBody ResetPasswordRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        ForgotPasswordToken forgotPasswordToken=forgotPasswordService.findById(id);
         boolean isVerified=forgotPasswordToken.getOtp().equals(req.getOtp());
         if(isVerified){
              userService.updatePassword(forgotPasswordToken.getUser(),req.getPassword());
             ApiResponse response=new ApiResponse();
             response.setMessage("Password update Successfully..");
             return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
         }
          throw new Exception("Wrong Otp you entered ...");
    }
}
