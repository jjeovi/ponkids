package com.meta.ponkids.domain.system.file.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * className      : AtchFile
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 파일 Entity
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where( clause = "del_yn = 'N'" )    // DEFAULT 로 WHERE DEL_YN = 'N' 문을 추가하여 조회
@SQLDelete( sql = "UPDATE tb_atch_file SET del_yn ='Y' WHERE atch_file_sn = ?" )
// delelte 시 실행 (ex ) ~Repository.deleteById)
@SequenceGenerator(
        name = "SEQ_TB_ATCH_FILE_SN",
        sequenceName = "SEQ_TB_ATCH_FILE_SN",
        initialValue = 1,
        allocationSize = 1
)
@EntityListeners( AuditingEntityListener.class )
@Entity
@Table( name = "tb_atch_file" )
public class AtchFile {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ATCH_FILE_SN" )
    private Long atchFileSn;
    
    @Comment( value = "등록일시" )
    @Column( updatable = false )
    @CreatedDate
    private LocalDateTime regDt;
    
    @ColumnDefault( "N" )                             // del_yn 컬럼에 공통으로 추가
    @Column( insertable = false, updatable = false )  // del_yn 컬럼에 공통으로 추가
    private String delYn;                           // 삭제 여부
    
}


