package com.meta.ponkids.domain.payment.service;


import com.meta.ponkids.domain.payment.entity.ClassPayment;
import com.meta.ponkids.domain.payment.entity.ClassPaymentHistory;
import com.meta.ponkids.domain.payment.repository.ClassPaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassPaymentHistoryService {
    
    private final ClassPaymentHistoryRepository classPaymentHistoryRepository;
    
    /**
     * 특정 결제(`classPaymentSn`)에 대한 다음 historySeq 값 가져오기
     *
     * @param classPaymentSn 원본 결제 PK
     * @return 다음 historySeq 값
     */
    public Long getNextHistorySeq( Long classPaymentSn ) {
        Long maxSeq = classPaymentHistoryRepository.findMaxHistorySeqByClassPaymentSn( classPaymentSn );
        return ( maxSeq == null ) ? 1 : maxSeq + 1;
    }
    
    /**
     * 결제 이력 저장
     */
    public void save( ClassPayment classPayment ) {
        Long nextSeq = getNextHistorySeq( classPayment.getClassPaymentSn() );
        
        ClassPaymentHistory classPaymentHistory = new ClassPaymentHistory( classPayment, nextSeq );
        
        classPaymentHistoryRepository.save( classPaymentHistory );
    }
    
}
