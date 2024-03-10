package com.meta.ponkids.domain.qestnar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meta.ponkids.domain.qestnar.entity.QestnarAnswerReply;
import com.meta.ponkids.domain.qestnar.repository.custom.QestnarAnswerReplyRepositoryCustom;

// TODO PK(*ID) 체크
public interface QestnarAnswerReplyRepository extends JpaRepository<QestnarAnswerReply, Long>, QestnarAnswerReplyRepositoryCustom {
	
	Optional<QestnarAnswerReply> findById( Long pk );	// TODO PK(*ID) 체크
}
