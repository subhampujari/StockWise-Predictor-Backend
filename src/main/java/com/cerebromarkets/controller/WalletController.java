package com.cerebromarkets.controller;


import com.cerebromarkets.Service.OrderService;
import com.cerebromarkets.Service.PaymentService;
import com.cerebromarkets.Service.UserService;
import com.cerebromarkets.Service.WalletService;
import com.cerebromarkets.modal.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/api/wallet")
    private ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Wallet wallet=walletService.getUserWallet(user);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/{walletId}/transfer")
    public  ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization")String jwt , @PathVariable Long walletId, @RequestBody WalletTransaction req) throws Exception{
        User senderUser=userService.findUserProfileByJwt(jwt);
        Wallet reciverWallet=walletService.findWalletById(walletId);
        Wallet wallet=walletService.walletToWalletTransfer(senderUser,reciverWallet,req.getAmount());
        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/order/{orderId}/pay")
    public  ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization")String jwt , @PathVariable Long orderId) throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        Order order=orderService.getOrderById(orderId);
        Wallet wallet=walletService.payOrderPayment(order,user);
        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/deposit")
    public ResponseEntity<Wallet> addBalanceToWallet(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(name = "order_id") Long orderId,
            @RequestParam(name = "payment_id") String paymentId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        PaymentOrder order = paymentService.getPaymentOrderByById(orderId);

        Boolean status = paymentService.proceedPaymentOrder(order, paymentId);

        if(wallet.getBalance() == null){
            wallet.setBalance(BigDecimal.valueOf(0));
        }

         if(status){
             wallet=walletService.addBalance(wallet,order.getAmount());
         }
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
}
