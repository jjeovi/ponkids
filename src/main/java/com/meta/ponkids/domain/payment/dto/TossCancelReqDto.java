package com.meta.ponkids.domain.payment.dto;


import com.meta.ponkids.domain.payment.refund.context.RefundContext;
import lombok.*;


/**
 * Toss 결제 취소 요청을 위한 데이터 전송 객체 (DTO).
 * 이 DTO는 결제 취소에 필요한 필드를 포함하고 있습니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TossCancelReqDto {
    
    /**
     * Toss에서 제공하는 고유 결제 키.
     * 이 값은 취소하려는 결제를 식별하는 데 사용됩니다.
     */
    private String paymentKey;
    
    /**
     * 취소할 금액.
     * 고객에게 환불될 금액을 나타냅니다.
     */
    private double cancelAmount;
    
    /**
     * 취소 사유.
     * 결제 취소 사유를 설명하는 문자열입니다.
     */
    private String cancelReason;
    
    
    // RefundContext로 초기화하는 생성자
    public TossCancelReqDto( RefundContext context ) {
        // RefundContext에서 필요한 값 추출
        this.paymentKey     = context.getClassPayment().getPaymentKey();                  // 결제 키
        this.cancelAmount   = context.getClassPayment().getCancelAmount().doubleValue();  // 환불 금액
        this.cancelReason   = "취소사유";                                                   // 환불 사유 (예시로 결제 이유를 사용)
    }
    
}
