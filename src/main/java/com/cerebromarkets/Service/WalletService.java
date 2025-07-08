package com.cerebromarkets.Service;

import com.cerebromarkets.modal.Order;
import com.cerebromarkets.modal.User;
import com.cerebromarkets.modal.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user);

    Wallet addBalance(Wallet wallet,Long money);

    Wallet findWalletById(Long id) throws Exception;

   Wallet walletToWalletTransfer(User user,Wallet reciveerWallet,Long amount) throws Exception;

   Wallet payOrderPayment(Order order,User user) throws Exception;
}
