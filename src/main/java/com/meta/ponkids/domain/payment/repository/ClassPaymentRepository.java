package com.meta.ponkids.domain.payment.repository;

import com.meta.ponkids.domain.payment.entity.ClassPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ClassPaymentRepository - 결제 정보를 관리하는 Repository
 * <p>
 * - 주문 ID, 결제 키 등으로 결제 정보를 조회할 수 있음
 * - 특정 결제 상태의 데이터를 가져오는 기능 포함
 */
@Repository
public interface ClassPaymentRepository extends JpaRepository<ClassPayment, Long> {
    /**
     * 주문 ID로 결제 정보 조회 (유니크 값이므로 단일 결과)
     *
     * @param orderId 주문 ID
     * @return 결제 정보 (Optional)
     */
    Optional<ClassPayment> findByOrderId( String orderId );
    
    // 또는, classReqstSn을 이용해서 찾기
    Optional<ClassPayment> findByClassReqst_ClassReqstSn(Long classReqstSn);
    
    
    /**
     * 결제 키로 결제 정보 조회 (유니크 값이므로 단일 결과)
     *
     * @param paymentKey 결제 키
     * @return 결제 정보 (Optional)
     */
    Optional<ClassPayment> findByPaymentKey( String paymentKey );
    
    /**
     * 특정 결제 상태의 모든 결제 내역 조회
     *
     * @param paymentStatus 결제 상태 (예: PENDING, COMPLETED)
     * @return 결제 상태에 해당하는 목록
     */
    List<ClassPayment> findByPaymentStatus( String paymentStatus);
    
}
