package com.cerebromarkets.Repository;

import com.cerebromarkets.modal.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderRepository  extends JpaRepository<PaymentOrder,Long> {
}
