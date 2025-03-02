package com.meta.ponkids.global.common;

import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


/**
 * className    : BaseTimeEntity
 * author         : jjeoV
 * date           : 10/29/23
 * description    : class of Time generation
 * 등록일 / 수정일 생성 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/29/23        jjeoV       최초 생성
 */
@Getter
@MappedSuperclass
@EntityListeners( AuditingEntityListener.class )
public abstract class BaseTimeEntity {
    
    @Comment( value = "등록일시" )
    @Column( updatable = false )
    @CreatedDate            // @EntityListeners( AuditingEntityListener.class ) 어노테이션이 있어야지만 동작한다.
    private LocalDateTime regDt;
    
    @Comment( value = "수정일시" )
//    @Column(insertable = false)   // 등록할 때도 수정일시에 시간 들어가게 변경
    @LastModifiedDate
    private LocalDateTime updtDt;
    
}
