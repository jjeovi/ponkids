package com.meta.ponkids.domain.payment.refund.context;

import com.meta.ponkids.domain.lctre.dto.LctreReqstListDto;
import com.meta.ponkids.domain.payment.entity.ClassPayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class RefundContext {
    
    private final List<LctreReqstListDto> lctreReqsts;    // 수업 신청 정보 ( 다건 )  -> 필요한 데이터 : 클래스제목, 수업이름, 수업금액, 수업일시
    private final LocalDateTime cancelRequestTime;  // 취소 요청 시각
    private final ClassPayment classPayment;        // 결제 정보
    
}
