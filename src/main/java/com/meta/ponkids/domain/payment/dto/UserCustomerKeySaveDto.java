package com.meta.ponkids.domain.payment.dto;

import com.meta.ponkids.domain.payment.PaymentProvider;
import com.meta.ponkids.domain.payment.entity.UserCustomerKey;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCustomerKeySaveDto {
    
    private Long userSn;             // 사용자 ID (필수)
    
    private PaymentProvider paymentProvider;     // 결제사 (TOSS, KAKAO 등)
    
    private String customerKey;          // 결제사에서 제공하는 customerKey 등
    
    
    public UserCustomerKey toEntity() {
        return UserCustomerKey.builder()
                .userSn( userSn )
                .paymentProvider( paymentProvider )
                .customerKey( customerKey )
                .build();
    }
}
