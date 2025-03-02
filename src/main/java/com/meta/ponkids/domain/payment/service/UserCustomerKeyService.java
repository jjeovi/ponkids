package com.meta.ponkids.domain.payment.service;

import com.meta.ponkids.domain.payment.PaymentProvider;
import com.meta.ponkids.domain.payment.dto.UserCustomerKeySaveDto;
import com.meta.ponkids.domain.payment.entity.UserCustomerKey;
import com.meta.ponkids.domain.payment.repository.UserCustomerKeyRepository;
import com.meta.ponkids.global.util.generator.CustomerKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCustomerKeyService {
    private final UserCustomerKeyRepository userCustomerKeyRepository;
    
    @Transactional
    public String getOrCreateCustomerKey( Long userSn, PaymentProvider paymentProvider ) {
        return userCustomerKeyRepository.findByUserSnAndPaymentProvider( userSn, paymentProvider )
                .map( UserCustomerKey::getCustomerKey )
                .orElseGet( () -> createAndSaveCustomerKey( userSn, paymentProvider ) );
    }
    
    private String createAndSaveCustomerKey( Long userSn, PaymentProvider paymentProvider ) {
        String newCustomerKey = CustomerKeyGenerator.generatorCustomerKey(); // 고객 키 생성 로직
        
        UserCustomerKey userCustomerKey = new UserCustomerKey( userSn, paymentProvider, newCustomerKey );
        userCustomerKeyRepository.save( userCustomerKey );
        return newCustomerKey;
    }
    
    // 결제 키 저장
    @Transactional
    public UserCustomerKeySaveDto save( UserCustomerKeySaveDto saveDto ) {
        UserCustomerKey userCustomerKey = saveDto.toEntity();
        
        return saveDto;
    }
    
    /**
     * 특정 유저(userSn)와 결제 제공업체(paymentProvider)에 해당하는 결제 키 조회
     *
     * @param userSn          유저 ID
     * @param paymentProvider 결제 제공업체 (TOSS, KAKAOPAY 등)
     * @return Optional<UserCustomerKey>
     */
    @Transactional( readOnly = true )
    public Optional<UserCustomerKey> findByUserSnAndPaymentProvider( Long userSn, PaymentProvider paymentProvider ) {
        return userCustomerKeyRepository.findByUserSnAndPaymentProvider( userSn, paymentProvider );
    }
}
