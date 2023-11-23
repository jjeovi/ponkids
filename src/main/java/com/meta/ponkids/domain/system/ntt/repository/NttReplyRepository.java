package com.meta.ponkids.domain.system.ntt.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.ponkids.domain.system.ntt.dto.NttReplyListDto;
import com.meta.ponkids.domain.system.ntt.entity.NttReply;
import com.meta.ponkids.domain.system.ntt.repository.custom.NttReplyRepositoryCustom;



public interface NttReplyRepository extends JpaRepository< NttReply, Integer>  , NttReplyRepositoryCustom {

	 public int MaxNttReplySeq(int nttSn);
	// public List<NttReplyListDto> getList(int nttSn); 

	 
	   // @Modifying( clearAutomatically = true )
	    @Query( value = " select a.nttReplySn as nttReplySn , a.nttSn as nttSn , a.step as step , a.parntsReplySn as  parntsReplySn , a.nttReplySeq as nttReplySeq ,a.nttReplyCn as nttReplyCn ,a.registerId as registerId  from ("
	            + "            select ntt_reply_sn as nttReplySn ,  ntt_sn as nttSn , step as step,   ntt_reply_sn as  parntsReplySn, ntt_reply_seq as nttReplySeq , ntt_reply_cn as nttReplyCn ,register_id as registerId"
	            + "                         from Tb_ntt_reply  where parnts_reply_sn   = 0  and ntt_sn = :nttSn"
	            + "      union all    "
	            + "            select ntt_reply_sn as nttReplySn ,  ntt_sn as nttSn , step as step,   parnts_reply_sn as  parntsReplySn, ntt_reply_seq as nttReplySeq , ntt_reply_cn as nttReplyCn ,register_id as registerId"
	            + "                         from Tb_ntt_reply  where parnts_reply_sn   != 0  and ntt_sn = :nttSn"
	            + "                       )  a order by a.parntsReplySn ,a.step  ", nativeQuery = true )
	    public List<NttReplyListDto> getList(@Param( "nttSn" ) int nttSn); 
			
	    

	    
}
