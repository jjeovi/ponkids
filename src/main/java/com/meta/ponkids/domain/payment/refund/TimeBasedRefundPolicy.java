package com.meta.ponkids.domain.payment.refund;

import com.meta.ponkids.domain.lctre.dto.LctreReqstListDto;
import com.meta.ponkids.domain.payment.refund.context.RefundContext;
import com.meta.ponkids.domain.payment.refund.log.RefundLogBuilder;
import com.meta.ponkids.domain.payment.refund.util.RefundPolicyRule;
import com.meta.ponkids.domain.payment.refund.util.RefundPolicyUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeBasedRefundPolicy implements RefundPolicy {
    
    
    /**
     * 단일 수업의 환불 금액을 계산합니다.
     * 수업 시작까지 남은 시간(시간 단위)에 따라 환불 비율이 정해지고, 그에 따라 금액을 반환합니다.
     * <p>
     * 수업 스케줄 시작 시각의 48시간 이전    : 취소 및 결제액 전액 환불
     * 48시간 전 ~ 24시간 이전            : 취소 및 수업 판매가의 50% 환불
     * 24시간 이내                        : 취소 및 환불 불가
     *
     * @param context 환불 컨텍스트 (수업 시작 시간, 취소 요청 시간, 결제 금액 등 포함)
     * @return 환불 금액 (정수 원 단위)
     **/
    @Override
    public RefundContext calculateRefundAmount( RefundContext context ) {
        
        // 1. 최상단 로그에 사용할 변수 준비
        BigDecimal totalPaymentAmount = context.getClassPayment().getAmount();    // 주문 전체 결제 금액
        BigDecimal totalLctreAmount = BigDecimal.ZERO;    // 수업의 총 금액
        BigDecimal totalRefundAmount = BigDecimal.ZERO;   // 총 환불 금액
        
        
        // 최종 환불 금액 초기값 설정
        
        StringBuilder log = new StringBuilder();
        
        for ( LctreReqstListDto lctreReqst : context.getLctreReqsts() ) {
            // 수업 시작 시각 파싱
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" );
            LocalDateTime startTime = LocalDateTime.parse( lctreReqst.getLctreDt(), formatter );
            long hoursUntilStart = Duration.between( context.getCancelRequestTime(), startTime ).toHours();
            
            // 환불율 결정
            BigDecimal refundAmt;                                                 // 환불 금액 ( 한 수업 기준 )
            BigDecimal amount = BigDecimal.valueOf( lctreReqst.getLctreAmt() );   // 수업 금액
            
            // 남은 시간 기준으로 환불 정책 분기
            // policyRule : 환불 정책 구분 Enum
            RefundPolicyRule policyRule;
            if ( hoursUntilStart >= 48 ) {
                policyRule = RefundPolicyRule.POLICY_1;
            } else if ( hoursUntilStart >= 24 ) {
                policyRule = RefundPolicyRule.POLICY_2;
            } else {
                policyRule = RefundPolicyRule.POLICY_3;
            }
            
            BigDecimal rate = RefundPolicyUtils.getRefundRateDecimal( policyRule );          // 환불율 가져오기
            
            refundAmt = amount.multiply( rate ).setScale( 0, RoundingMode.DOWN );   // 환불 금액 계산
            
            // 9. 로그 한 줄 누적
            RefundLogBuilder.appendLogForLctre( log, lctreReqst, refundAmt, policyRule );
            
            // 수업의 총 금액 누적
            totalLctreAmount = totalLctreAmount.add( amount );
            
            // 10. 총 환불 금액에 현재 수업의 환불 금액 누적
            totalRefundAmount = totalRefundAmount.add( refundAmt );
            
        }
        
        // 5. 최상단 로그 작성: 주문 금액, 수업 금액, 환불 금액
        boolean isMatch = totalPaymentAmount.compareTo( totalLctreAmount ) == 0;    // 주문전체결제금액과 수업의 총금액 비교
        RefundLogBuilder.appendTopLevelLog( log, context.getClassPayment(), totalLctreAmount, totalRefundAmount, isMatch );
        
        // 6. 주문전체결제금액과 수업의 총금액이 다르면 어떻게 조치할지 결정 필요
        if ( !isMatch ) {
            // TODO
        }
        
        // 11. 로그 전체를 ClassPayment의 reason 필드에 저장
        context.getClassPayment().updateReason( log.toString() );
        
        // 8. 환불 금액을 RefundContext의 classPayment.refundAmount에 저장
        context.getClassPayment().setCancelAmount( totalRefundAmount );
        
        // 9. 계산된 RefundContext 객체 반환
        return context;
        
    }
    
    
}
