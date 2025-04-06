package com.meta.ponkids.domain.payment.refund.util;

/**
 * 환불 정책 구분 Enum.
 *
 * POLICY_1 : 수업 시작 48시간 이전
 * POLICY_2 : 수업 시작 24시간 ~ 48시간 전
 * POLICY_3 : 수업 시작 24시간 이내
 */
public enum RefundPolicyRule {
    POLICY_1, // 48시간 이전
    POLICY_2, // 24~48시간 사이
    POLICY_3  // 24시간 이내
}
