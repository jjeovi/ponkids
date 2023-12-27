package com.meta.ponkids.domain.ntt.repository;


import com.meta.ponkids.domain.ntt.dto.NttReplyListDto;
import com.meta.ponkids.domain.ntt.entity.NttReply;
import com.meta.ponkids.domain.ntt.repository.custom.NttReplyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface NttReplyRepository extends JpaRepository<NttReply, Long>, NttReplyRepositoryCustom {
    
    int MaxNttReplySeq( Long nttSn );
    
    List<NttReplyListDto> getList( Long nttSn );
    
    List<NttReplyListDto> getAnswerReplyList( Long nttReplySn );
    
    NttReply findByNttReplySn( Long nttReplySn );
    
    
    @Modifying( clearAutomatically = true )
    @Query( value = "UPDATE tb_ntt_reply "
            + "      SET del_yn = 'Y'"
            + "        , updt_dt = now() "
            + "    WHERE ntt_reply_sn = :nttReplySn", nativeQuery = true )
        // nativeQuery true 없으면 error
    int deleteAllByNttReplySn( @Param( "nttReplySn" ) Long nttReplySn );
    
    
}
