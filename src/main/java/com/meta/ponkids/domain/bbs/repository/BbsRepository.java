package com.meta.ponkids.domain.bbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.ponkids.domain.bbs.entity.Bbs;
import com.meta.ponkids.domain.bbs.repository.custom.BbsRepositoryCustom;


public interface BbsRepository extends JpaRepository< Bbs, Long > , BbsRepositoryCustom {

   boolean existsByBbsSn(Long bbsSn);
   
   public Bbs findByBbsSn(Long bbsSn);
   
   // 게시판 삭제
   @Modifying( clearAutomatically = true )
   @Query( value = "UPDATE tb_bbs "
           + "      SET del_yn = 'Y'"
           + "        , updt_dt = now() "
           + "    WHERE bbs_sn = :bbsSn", nativeQuery = true )
       // nativeQuery true 없으면 error
   int deleteAllByBbsSn( @Param( "bbsSn" ) Long bbsSn );
   
   // 게시판 댓글 설정여부 조회
   public String getSetReplySetYn(Long bbsSn);
   
   // 개시판 구분 코드 조회
   public String getBbsSeCd(Long bbsSn);
   

	    
}
