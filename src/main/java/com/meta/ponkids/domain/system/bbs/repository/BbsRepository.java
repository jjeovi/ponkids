package com.meta.ponkids.domain.system.bbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.ponkids.domain.system.bbs.entity.Bbs;
import com.meta.ponkids.domain.system.bbs.repository.custom.BbsRepositoryCustom;



public interface BbsRepository extends JpaRepository< Bbs, Integer> , BbsRepositoryCustom {

   boolean existsByBbsSn(int bbsSn);
   
   public Bbs findByBbsSn(int bbsSn);

	    
}
