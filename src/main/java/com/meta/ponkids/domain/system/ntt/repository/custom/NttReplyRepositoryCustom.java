package com.meta.ponkids.domain.system.ntt.repository.custom;

import com.meta.ponkids.domain.system.ntt.dto.NttReplyListDto;
import java.util.List;



public interface NttReplyRepositoryCustom {
	List<NttReplyListDto> getList(Long nttSn );
	List<NttReplyListDto> getAnswerReplyList(Long nttReplySn );
	List<NttReplyListDto> getInfoList(Long nttReplySn );
	
	

}
