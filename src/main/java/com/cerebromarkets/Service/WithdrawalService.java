package com.cerebromarkets.Service;

import com.cerebromarkets.modal.User;
import com.cerebromarkets.modal.Withdrawal;

import java.util.List;

public interface WithdrawalService {
    Withdrawal requestwithdrawal(Long amount, User user);

    Withdrawal procedWithdrawal(Long withdrawalId,boolean accept) throws Exception;

    List<Withdrawal> getUserWithdrawalHistory(User user);

    List<Withdrawal> getAllWithdrawalRequest();
}
