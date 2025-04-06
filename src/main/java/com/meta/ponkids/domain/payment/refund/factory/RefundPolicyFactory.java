package com.meta.ponkids.domain.payment.refund.factory;

import com.meta.ponkids.domain.payment.refund.RefundPolicy;
import com.meta.ponkids.domain.payment.refund.TimeBasedRefundPolicy;
import com.meta.ponkids.domain.payment.refund.context.RefundContext;

public class RefundPolicyFactory {
    
    public RefundPolicy getPolicy( RefundContext context) {
        
        // 추후 예시..
//        if (context.isAdminCancel()) {
//            return new AdminFullRefundPolicy();
//        }
//
//        if (context.getLectureRequest().isPromotion()) {
//            return new PromotionRefundPolicy();
//        }
        
        // 추후 조건 분기 대비 구조. 지금은 하나만 반환
        return new TimeBasedRefundPolicy();
    }
    
    
}
