package com.meta.ponkids.domain.adm.lctre.repository;

import java.util.Optional;

import com.meta.ponkids.domain.adm.lctre.entity.Lctre;
import com.meta.ponkids.domain.adm.lctre.repository.custom.LctreRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO PK(*ID) 체크
public interface LctreRepository extends JpaRepository<Lctre, Long>, LctreRepositoryCustom {
	
	Optional<Lctre> findById( Long pk );	// TODO PK(*ID) 체크
	
	Optional<Lctre> findTop1ByClassSnOrderByLctreSeqDesc( Long pk );
}
