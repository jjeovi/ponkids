package com.meta.ponkids.domain.payment.repository;

import com.meta.ponkids.domain.payment.PaymentProvider;
import com.meta.ponkids.domain.payment.entity.UserCustomerKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCustomerKeyRepository extends JpaRepository<UserCustomerKey, Long> {
    Optional<UserCustomerKey> findByUserSnAndPaymentProvider( Long userSn, PaymentProvider paymentProvider );
    
}
