package com.meta.ponkids.domain.lctre.repository;

import java.util.List;
import java.util.Optional;

import com.meta.ponkids.domain.lctre.entity.Lctre;
import com.meta.ponkids.domain.lctre.repository.custom.LctreRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LctreRepository extends JpaRepository<Lctre, Long>, LctreRepositoryCustom {
	
	Optional<Lctre> findById( Long pk );	// TODO PK(*ID) 체크
	
	Optional<Lctre> findTop1ByClassSnOrderByLctreSeqDesc( Long pk );
	
	List<Lctre> findByClassSnAndClassDayCdOrderByLctreSeqAsc( Long classSn, String classDayCd );
}
