package com.meta.ponkids.domain.system.ntt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.system.ntt.entity.Ntt;
import com.meta.ponkids.domain.system.ntt.entity.NttReply;
import com.meta.ponkids.domain.system.ntt.repository.custom.NttReplyRepositoryCustom;



public interface NttReplyRepository extends JpaRepository< NttReply, Integer>  , NttReplyRepositoryCustom {

	   public int MaxNttReplySeq(int nttSn);



	    
}
