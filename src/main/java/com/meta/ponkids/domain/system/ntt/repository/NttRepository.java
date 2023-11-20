package com.meta.ponkids.domain.system.ntt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.meta.ponkids.domain.system.ntt.entity.Ntt;
import com.meta.ponkids.domain.system.ntt.repository.custom.NttRepositoryCustom;



public interface NttRepository extends JpaRepository< Ntt, Integer> , NttRepositoryCustom {

   boolean existsByNttSn(int nttSn);
   
   public Ntt findByNttSn(int nttSn);


	    
}
