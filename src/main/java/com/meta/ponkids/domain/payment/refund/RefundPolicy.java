package com.meta.ponkids.domain.payment.refund;


import com.meta.ponkids.domain.payment.refund.context.RefundContext;

import java.math.BigDecimal;

/**
 * 환불 정책 인터페이스.
 * 시간 또는 조건에 따른 환불율을 계산하는 정책 구현체들이 이 인터페이스를 구현한다.
 */
public interface RefundPolicy {
    /**
     * 환불 금액을 계산합니다.
     *
     * @param context 환불 정책 계산에 필요한 모든 정보를 담은 컨텍스트
     * @return 환불 금액
     */
    RefundContext calculateRefundAmount( RefundContext context );
    
}
