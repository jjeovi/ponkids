package com.meta.ponkids.domain.payment.repository;

import com.meta.ponkids.domain.payment.entity.ClassPaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassPaymentHistoryRepository extends JpaRepository<ClassPaymentHistory, Long> {
    
    /**
     * 특정 결제의 가장 높은 historySeq 조회
     *
     * @param classPaymentSn 결제 PK
     * @return 현재 최대 historySeq 값 (없으면 NULL)
     */
    @Query( value = "SELECT MAX(h.historySeq)"
                  + "  FROM ClassPaymentHistory h"
                  + " WHERE h.classPaymentSn = :classPaymentSn" )
    Long findMaxHistorySeqByClassPaymentSn( @Param( "classPaymentSn" ) Long classPaymentSn );
}
