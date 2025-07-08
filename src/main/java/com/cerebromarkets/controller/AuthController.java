package com.cerebromarkets.controller;


import com.cerebromarkets.Repository.UserRepository;
import com.cerebromarkets.Service.CustomerUserDetailsService;
import com.cerebromarkets.Service.EmailService;
import com.cerebromarkets.Service.TwoFactorOtpService;
import com.cerebromarkets.Service.WatchListService;
import com.cerebromarkets.config.JwtProvider;
import com.cerebromarkets.modal.TwoFactorOTP;
import com.cerebromarkets.modal.User;
import com.cerebromarkets.response.AuthResponse;
import com.cerebromarkets.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private EmailService emailService;


    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private UserRepository userRepository;

     @Autowired
     private WatchListService watchListService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist != null) {
            throw new Exception("email is already used with another account");
        }

        User newdUser = new User();
        newdUser.setEmail(user.getEmail());
        newdUser.setPassword(user.getPassword());
        newdUser.setFullName(user.getFullName());
        User savedUser = userRepository.save(newdUser);
        watchListService.createWatchList(savedUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("register success");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {
        String username = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);
        User authUser = userRepository.findByEmail(username);
        if (user.getTwoFactorAuth().isEnabled()) {
            AuthResponse res = new AuthResponse();
            res.setMessage("Two Factor auth is enable");
            res.setTwoFactorAuthEnabled(true);
            String otp = OtpUtils.generateOtp();
            TwoFactorOTP oldTwoFactorOtp = twoFactorOtpService.findByUser(authUser.getId());
            if (oldTwoFactorOtp != null) {
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOtp);
            }
            TwoFactorOTP newTwoFactorOTP = twoFactorOtpService.createTwoFactorOtp(authUser, otp, jwt);
            emailService.sendVerificationOtpEmail(username,otp);
            res.setSession(newTwoFactorOTP.getId());
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("login  success");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid User Name");
        }
        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Password is wrong");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySignInOtp(@PathVariable String otp,@RequestParam String id) throws Exception {
       TwoFactorOTP twoFactorOTP=twoFactorOtpService.findById(id);
       if(twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP,otp)){
              AuthResponse res=new AuthResponse();
              res.setMessage("Two Factor Authentication Verified");
              res.setTwoFactorAuthEnabled(true);
              res.setJwt(twoFactorOTP.getJwt());
              return new ResponseEntity<>(res,HttpStatus.OK);
       }
       throw  new Exception("invalid otp");
    }
}
