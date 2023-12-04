package com.meta.ponkids.domain.system.ntt.repository.custom;

import com.meta.ponkids.domain.system.ntt.dto.NttReplyListDto;
import java.util.List;



/**
 * InterfaceName  : NttReplyRepositoryCustom
 * author         : ehlee
 * date           : 2023-12-02
 * description    : interface of 게시물 댓글 RepositoryCustom
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-12-02        ehlee             최초 생성
 */
public interface NttReplyRepositoryCustom {
	
	List<NttReplyListDto> getList(Long nttSn );
	List<NttReplyListDto> getAnswerReplyList(Long nttReplySn );

}
