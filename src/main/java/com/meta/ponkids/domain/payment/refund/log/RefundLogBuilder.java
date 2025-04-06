package com.meta.ponkids.domain.payment.refund.log;

import com.meta.ponkids.domain.lctre.dto.LctreReqstListDto;
import com.meta.ponkids.domain.payment.entity.ClassPayment;
import com.meta.ponkids.domain.payment.refund.util.RefundPolicyRule;
import com.meta.ponkids.domain.payment.refund.util.RefundPolicyUtils;

import java.math.BigDecimal;

public class RefundLogBuilder {
    
    /**
     * 환불 로그를 전체 포맷
     * 예시 출력:
     * [ 주문전체금액 : 70000원 , 수업의 총금액 : 70000원  (일치) ] ->  환불금액 : 45000원
     * 주문목록
     * [봄학기반]음악기초 - 수업금액 : 50000원 / 수업시작일시 : 2025-04-22 14:00 / 환불요청일시 : 2025-04-20 22:00 / 환불요청시점이 수업시작 24시간전~48시간전 사이  / 50%환불  / 환불금액 : 25000원
     * [봄학기반]미술기초 - 수업금액 : 20000원 / 수업시작일시 : 2025-04-25 14:00 / 환불요청일시 : 2025-04-20 22:00 / 환불요청시점이 수업시작 48시간 이전           / 100%환불 / 환불금액 : 20000원
     * <p>
     * /
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * /**
     * <p>
     * 개별 수업에 대한 환불 로그 한 줄을 StringBuilder에 추가합니다.
     *
     * <p>
     * 예시 출력:
     * [봄학기반]음악기초 - 수업금액 : 50000원 / 수업시작일시 : 2025-04-22 14:00 / 환불요청일시 : 2025-04-20 22:00 / 환불요청시점이 수업시작 24시간전~48시간전 사이  / 50%환불  / 환불금액 : 25000원
     *
     * @param log        로그를 누적할 StringBuilder 객체
     * @param lctreReqst 환불 로그를 작성할 수업 정보 객체 (수업명, 클래스명, 수업금액 등)
     * @param refundAmt  해당 수업에 대한 환불 금액
     * @param policyRule 적용된 환불 정책 (예: 100%, 50%, 0%)
     */
    public static void appendLogForLctre( StringBuilder log,
                                          LctreReqstListDto lctreReqst,
                                          BigDecimal refundAmt,
                                          RefundPolicyRule policyRule ) {
        // 수업 시작일시 포맷팅
        String formattedStartTime = lctreReqst.getLctreDt(); // 이미 포맷된 문자열
        
        // 환불율 및 설명 가져오기
        String policyDesc = RefundPolicyUtils.getDescription( policyRule );
        String rateText = RefundPolicyUtils.getRefundRatePercent( policyRule ) + "%";
        
        // 로그 한 줄 작성
        log.append( "[" ).append( lctreReqst.getClassSj() ).append( "] " ).append( lctreReqst.getLctreSj() ) // 수업 이름
                .append( " - 수업금액 : " ).append( String.format( "%,d", lctreReqst.getLctreAmt() ) ).append( "원" )    // 수업 금액 (쉼표 포맷)
                .append( " / 수업시작일시 : " ).append( formattedStartTime ) // 수업 시작일시
                .append( " / 정책 기준 : " ).append( policyDesc ) // 환불 정책 설명
                .append( " / 환불율 : " ).append( rateText ).append( "환불" ) // 환불율
                .append( " / 환불금액 : " ).append( String.format( "%,d", refundAmt.longValue() ) ).append( "원" )// 환불 금액 (쉼표 포맷)
                .append( "\n" );
    }
    
    
    /**
     * 최상단 로그를 생성하여 StringBuilder에 추가합니다.
     * <p>
     * 예시 출력:
     * <p>
     * [ 주문전체금액 : 70000원 , 수업의 총금액 : 70000원  (일치) ] ->  환불금액 : 45000원
     * 주문목록
     *
     * @param log               로그를 누적할 StringBuilder 객체
     * @param classPayment      클래스 결제 정보 (주문 전체 금액)
     * @param totalLctreAmount  수업 금액 총합
     * @param totalRefundAmount 환불 금액 총합
     */
    public static void appendTopLevelLog( StringBuilder log, ClassPayment classPayment, BigDecimal totalLctreAmount, BigDecimal totalRefundAmount, boolean isMatch ) {
        
        // 최상단 로그 작성 (주문 금액, 수업 금액, 환불 금액)
        log.insert( 0, "[ 주문전체금액 : " + String.format( "%,d", classPayment.getAmount().longValue() ) + "원 , "
                + "수업의 총금액 : " + String.format( "%,d", totalLctreAmount.longValue() ) + "원  (" + ( isMatch ? "일치" : "불일치" ) + ") ]"
                + " -> 환불금액 : " + String.format( "%,d", totalRefundAmount.longValue() ) + "원\n주문목록\n" );
    }
    
}
