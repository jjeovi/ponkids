package com.meta.ponkids.domain.payment;
/**
 * PaymentProvider Enum
 *
 * 이 Enum은 결제 제공자(Payment Provider)를 정의합니다.
 * 특정 결제 수단을 선택할 때, 허용된 값만 사용할 수 있도록 제한합니다.
 *
 * 사용 예시:
 * PaymentProvider provider = PaymentProvider.TOSS;
 */
public enum PaymentProvider {
    /** 토스페이먼츠 */
    TOSS,
    
    /** 카카오페이 */
    KAKAOPAY,
    
    /** 페이팔 */
    PAYPAL,
    
}

