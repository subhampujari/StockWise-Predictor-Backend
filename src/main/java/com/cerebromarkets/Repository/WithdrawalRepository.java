package com.cerebromarkets.Repository;

import com.cerebromarkets.modal.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WithdrawalRepository  extends JpaRepository<Withdrawal,Long> {

    List<Withdrawal> findByUserId(Long  userId);
}
