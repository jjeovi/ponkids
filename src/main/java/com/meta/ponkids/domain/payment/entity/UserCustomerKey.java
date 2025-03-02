package com.meta.ponkids.domain.payment.entity;

import com.meta.ponkids.domain.payment.PaymentProvider;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "tb_user_customer_key", uniqueConstraints = {
        @UniqueConstraint( name = "unique_user_payment", columnNames = { "user_sn", "payment_provider" } )
} )
@Getter
@EntityListeners( AuditingEntityListener.class )
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCustomerKey {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // BIGSERIAL과 매칭됨
    private Long userCustomerKeySn;  // 자동 증가하는 기본 키
    
    @Column( name = "user_sn", nullable = false )
    private Long userSn;  // tb_user 테이블의 PK (사용자 식별값)
    
    @Enumerated( EnumType.STRING )
    @Column( name = "payment_provider", nullable = false, length = 32 )
    private PaymentProvider paymentProvider;  // 결제사 (TOSS, KAKAO, PAYCO 등)
    
    @Column( name = "customer_key", nullable = false, unique = true, length = 64 )
    private String customerKey;  // 결제사별 사용자 고유 키 (customerKey, partner_user_id 등)
    
    @Comment( value = "등록일시" )
    @Column( name="reg_dt", updatable = false )
    @CreatedDate
    private LocalDateTime regDt;
    
    @Builder
    public UserCustomerKey( Long userSn, PaymentProvider paymentProvider, String newCustomerKey ) {
        this.userSn = userSn;
        this.paymentProvider = paymentProvider;
        this.customerKey = newCustomerKey;
    }
}