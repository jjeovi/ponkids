package com.meta.ponkids.domain.payment.refund.util;


import java.math.BigDecimal;

/**
 * RefundPolicyRule 에 대한 설명과 환불율을 제공하는 유틸 클래스
 */
public class RefundPolicyUtils {
    
    /**
     * 환불 정책 설명을 반환합니다.
     *
     * @param rule 적용된 환불 정책
     * @return 사람에게 보여줄 정책 설명
     */
    public static String getDescription(RefundPolicyRule rule) {
        return switch (rule) {
            case POLICY_1 -> "환불요청시점이 수업시작 48시간 이전";
            case POLICY_2 -> "환불요청시점이 수업시작 24시간전~48시간전 사이 ";
            case POLICY_3 -> "환불요청시점이 수업시작 24시간 이내";
        };
    }
    
    /**
     * 환불율을 정수(%)로 반환합니다.
     *
     * @param rule 적용된 환불 정책
     * @return 환불율 (예: 100, 50, 0)
     */
    public static int getRefundRatePercent(RefundPolicyRule rule) {
        return switch (rule) {
            case POLICY_1 -> 100;
            case POLICY_2 -> 50;
            case POLICY_3 -> 0;
        };
    }
    
    /**
     * 환불율을 BigDecimal 형태로 반환합니다. (계산용)
     *
     * @param rule 적용된 환불 정책
     * @return 1.0, 0.5, 0.0 등
     */
    public static BigDecimal getRefundRateDecimal( RefundPolicyRule rule) {
        return switch (rule) {
            case POLICY_1 -> BigDecimal.valueOf(1.0);
            case POLICY_2 -> BigDecimal.valueOf(0.5);
            case POLICY_3 -> BigDecimal.ZERO;
        };
    }
    
    
    
    
}
