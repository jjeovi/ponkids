package com.meta.ponkids.domain.payment.dto;

import com.meta.ponkids.domain.payment.PaymentProvider;
import com.meta.ponkids.domain.payment.entity.UserCustomerKey;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCustomerKeyListDto {
    
    private Long userCustomerKeySn;             // PK (조회 전용)
    
    private Long userSn;                // 사용자 ID
    
    private PaymentProvider paymentProvider;     // 결제사
    
    private String customerKey;          // 결제사에서 제공하는 customerKey 등
    
//    private LocalDateTime regDt;        // 생성 일자 (조회 전용)
    
    public UserCustomerKeyListDto toDto( UserCustomerKey userCustomerKey ) {
        return UserCustomerKeyListDto.builder()
                .userCustomerKeySn( userCustomerKey.getUserCustomerKeySn() )
                .userSn( userCustomerKey.getUserSn() )
                .paymentProvider( userCustomerKey.getPaymentProvider() )
                .customerKey( userCustomerKey.getCustomerKey() )
                .build();
    }
}
