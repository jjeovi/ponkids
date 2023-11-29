package com.meta.ponkids.domain.system.ntt.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.ponkids.domain.system.ntt.dto.NttReplyListDto;
import com.meta.ponkids.domain.system.ntt.entity.Ntt;
import com.meta.ponkids.domain.system.ntt.entity.NttReply;
import com.meta.ponkids.domain.system.ntt.repository.custom.NttReplyRepositoryCustom;



public interface NttReplyRepository extends JpaRepository< NttReply, Long>  , NttReplyRepositoryCustom {

	 public int MaxNttReplySeq(Long nttSn);
	 public List<NttReplyListDto> getList(Long nttSn); 
	 public List<NttReplyListDto> getAnswerReplyList(Long nttReplySn); 
	 public List<NttReplyListDto> getInfoList(Long nttReplySn); 
     public NttReply findByNttReplySn(Long nttReplySn);
     
     
     @Modifying( clearAutomatically = true )
     @Query( value = "UPDATE tb_ntt_reply "
             + "      SET del_yn = 'Y'"
             + "        , updt_dt = now() "
             + "    WHERE ntt_reply_sn = :nttReplySn", nativeQuery = true )
         // nativeQuery true 없으면 error
     int deleteAllByNttReplySn( @Param( "nttReplySn" ) Long nttReplySn );



	    

	    
}
