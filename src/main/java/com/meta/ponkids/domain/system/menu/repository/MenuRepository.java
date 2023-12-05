package com.meta.ponkids.domain.system.menu.repository;

import com.meta.ponkids.domain.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.system.menu.entity.Menu;
import com.meta.ponkids.domain.system.menu.repository.custom.MenuRepositoryCustom;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// TODO PK(*ID) 체크
public interface MenuRepository extends JpaRepository<Menu, Long>, MenuRepositoryCustom {
    
    Optional<Menu> findById( Long pk );    // TODO PK(*ID) 체크
    
    void deleteByMenuSn( Long sn );
    
    Menu findTop1ByUpdtDtIsNotNullOrderByUpdtDtDesc();
    
    @Query( value = "select menu_url"
    		+		"  from tb_menu"
    		+ 		" where del_yn = 'N'"
    		+ 		"   and use_yn = 'Y'"
    		+ 		"   and menu_url ~ :srchUrl"
    		+ 		" limit 1", 	nativeQuery = true )
    String findBymenuUrlRegExp(@Param("srchUrl" ) String srchUrl);
    
}
