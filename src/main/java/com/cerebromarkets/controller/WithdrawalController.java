package com.cerebromarkets.controller;

import com.cerebromarkets.Service.UserService;
import com.cerebromarkets.Service.WalletService;
import com.cerebromarkets.Service.WithdrawalService;
import com.cerebromarkets.domain.WalletTransactionType;
import com.cerebromarkets.modal.User;
import com.cerebromarkets.modal.Wallet;
import com.cerebromarkets.modal.WalletTransaction;
import com.cerebromarkets.modal.Withdrawal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;



    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<?> withdrawalRequest(
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);

        Withdrawal withdrawal = withdrawalService.requestwithdrawal(amount,user);
        walletService.addBalance(userWallet, -withdrawal.getAmount());
//
//        WalletTransaction walletTransaction = walletTransactionService.createTransaction(
//                userWallet,
//                WalletTransactionType.WITHDRAWAL, null,
//                "bank account withdrawal",
//                withdrawal.getAmount()
//        );

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }
    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(
            @PathVariable Long id,
            @PathVariable boolean accept,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Withdrawal withdrawal = withdrawalService.procedWithdrawal(id, accept);

        Wallet userWallet = walletService.getUserWallet(user);
        if (!accept) {
            walletService.addBalance(userWallet, withdrawal.getAmount());
        }

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        List<Withdrawal> withdrawal = withdrawalService.getAllWithdrawalRequest();

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }
}

