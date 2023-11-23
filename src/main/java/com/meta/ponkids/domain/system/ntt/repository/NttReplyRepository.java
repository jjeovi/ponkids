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
	 public List<NttReplyListDto> getList(int nttSn); 

	 

	    

	    
}
