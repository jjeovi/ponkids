package com.meta.ponkids.domain.system.menu.repository.impl;


import com.meta.ponkids.domain.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.system.menu.dto.QMenuListDto;
import com.meta.ponkids.domain.system.menu.entity.Menu;
import com.meta.ponkids.domain.system.menu.repository.custom.MenuRepositoryCustom;
import com.meta.ponkids.domain.system.role.dto.QRoleListDto;
import com.meta.ponkids.domain.system.role.dto.RoleListDto;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.meta.ponkids.domain.system.menu.entity.QAdminMenuHierarchy.adminMenuHierarchy;
import static com.meta.ponkids.domain.system.menu.entity.QMenu.menu;
import static com.meta.ponkids.domain.system.menu.entity.QMenuRole.menuRole;
import static com.meta.ponkids.domain.system.role.entity.QRole.role;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepositoryCustom {
    
    private final JPAQueryFactory query;
    
    @Override
    public List<MenuListDto> getList( MenuListDto listDto ) {
        
        System.out.println("캐시를 저장시에 사용합니다. 최초 이후로는 저장이 되지 않습니다.");
        //		   select tmr.menu_sn
        //				, tmr.role_sn
        //				, vmh.upper_menu_sn
        //				, vmh.menu_nm
        //				, vmh.menu_cd
        //				, vmh.menu_url
        //				, vmh.parnts_menu_yn
        //				, vmh.menu_seq
        //				, vmh.menu_dc_set_yn
        //				, vmh.menu_dc
        //				, vmh.menu_detail_dc
        //				, vmh.atch_file_sn
        //				, vmh.use_yn
        //				, vmh.new_window_yn
        //		     from tb_menu_role tmr
        //	   	     left join vw_menu_hierarchy vmh
        //		       on tmr.menu_sn = vmh.menu_sn
        //		    where tmr.role_sn = '100002'
        //          order by vmh."hierarchy" asc;
        
        // (1) 결과list (results).
        List<MenuListDto> results = query
                // select
                .select( new QMenuListDto(
                        adminMenuHierarchy.menuSn
                        , adminMenuHierarchy.menuSn.as( "id" )    // menuSn 과 id 는 같은 값으로 mapping (jstree 의 변수 id를 매핑하기 위한 임시 변수)
                        , menuRole.roleSn
                        , adminMenuHierarchy.upperMenuSn
                        , new CaseBuilder()
                        .when( adminMenuHierarchy.upperMenuSn.isNull() ).then( "#" )
                        .otherwise( adminMenuHierarchy.upperMenuSn.stringValue() ).as( "parent" )
                        // upperMenuSn 과 parent 는 같은 값으로 mapping (jstree 의 변수 parent를 매핑하기 위한 임시 변수)
                        // parent 는 string 임에 유의한다. parent가 null 이면 jstree가 error 발생하여 null 을 # 로 치환
                        , adminMenuHierarchy.menuNm
                        , adminMenuHierarchy.menuNm.concat( " [" ).concat( adminMenuHierarchy.menuSeq.stringValue() ).concat( "]" ).as( "text" )    // menuNm 과 text 는 같은 값으로 mapping ( jstree 의 변수 text를 매핑하기 위한 임시 변수 )
                        , adminMenuHierarchy.menuPath
                        , adminMenuHierarchy.hierarchy
                        , adminMenuHierarchy.requiredMenu
                        , adminMenuHierarchy.childMenuCnt
                        , adminMenuHierarchy.menuCd
                        , adminMenuHierarchy.menuUrl
                        , adminMenuHierarchy.parntsMenuYn
                        , adminMenuHierarchy.parntsMenuYn.as( "types" )
                        , adminMenuHierarchy.menuSeq
                        , adminMenuHierarchy.menuDcSetYn
                        , adminMenuHierarchy.menuDc
                        , adminMenuHierarchy.menuDetailDc
                        , adminMenuHierarchy.atchFileSn
                        , adminMenuHierarchy.useYn
                        , adminMenuHierarchy.newWindowYn
                        , adminMenuHierarchy.level
                ) )
                .from( menuRole )
                .leftJoin( adminMenuHierarchy )    // view : vw_menu_hierarchy
                .on(
                        menuRole.menuSn.eq( adminMenuHierarchy.menuSn )
                )
                .where(
                        eqCateLv1( listDto.getCategory() )
                )
                .orderBy(
                        adminMenuHierarchy.hierarchy.asc()
                ).fetch();
        
        return results;
        
    }
    
    @Override
    public List<MenuListDto> getListAgain( MenuListDto listDto ) {
        System.out.println("캐시를 갱신할 때 사용됩니다.");
        return this.getList( listDto );
    }
    
    @Override
    public List<MenuListDto> getAllList( MenuListDto listDto ) {
        
        System.out.println("캐시를 저장시에 사용합니다. 최초 이후로는 실행되지 않습니다..");
        
        //		   select tmr.menu_sn
        //				, tmr.role_sn
        //				, vmh.upper_menu_sn
        //				, vmh.menu_nm
        //				, vmh.menu_cd
        //				, vmh.menu_url
        //				, vmh.parnts_menu_yn
        //				, vmh.menu_seq
        //				, vmh.menu_dc_set_yn
        //				, vmh.menu_dc
        //				, vmh.menu_detail_dc
        //				, vmh.atch_file_sn
        //				, vmh.use_yn
        //				, vmh.new_window_yn
        //		     from vw_menu_hierarchy vmh
        //       order by vmh."hierarchy" asc;
        
        // (1) 결과list (results).
        List<MenuListDto> results = query
                // select
                .select( new QMenuListDto(
                        adminMenuHierarchy.menuSn
                        , adminMenuHierarchy.menuSn.as( "id" )    // menuSn 과 id 는 같은 값으로 mapping (jstree 의 변수 id를 매핑하기 위한 임시 변수)
                        , Expressions.asString( "0" ).castToNum( Long.class ).as( "roleSn" ) // 0
                        , adminMenuHierarchy.upperMenuSn
                        , new CaseBuilder()
                        .when( adminMenuHierarchy.upperMenuSn.isNull() ).then( "#" )
                        .otherwise( adminMenuHierarchy.upperMenuSn.stringValue() ).as( "parent" )
                        // upperMenuSn 과 parent 는 같은 값으로 mapping (jstree 의 변수 parent를 매핑하기 위한 임시 변수)
                        // parent 는 string 임에 유의한다. parent가 null 이면 jstree가 error 발생하여 null 을 # 로 치환
                        , adminMenuHierarchy.menuNm
                        , adminMenuHierarchy.menuNm.concat( " [" ).concat( adminMenuHierarchy.menuSeq.stringValue() ).concat( "]" ).as( "text" )    // menuNm 과 text 는 같은 값으로 mapping ( jstree 의 변수 text를 매핑하기 위한 임시 변수 )
                        , adminMenuHierarchy.menuPath
                        , adminMenuHierarchy.hierarchy
                        , adminMenuHierarchy.requiredMenu
                        , adminMenuHierarchy.childMenuCnt
                        , adminMenuHierarchy.menuCd
                        , adminMenuHierarchy.menuUrl
                        , adminMenuHierarchy.parntsMenuYn
                        , adminMenuHierarchy.parntsMenuYn.as( "types" )
                        , adminMenuHierarchy.menuSeq
                        , adminMenuHierarchy.menuDcSetYn
                        , adminMenuHierarchy.menuDc
                        , adminMenuHierarchy.menuDetailDc
                        , adminMenuHierarchy.atchFileSn
                        , adminMenuHierarchy.useYn
                        , adminMenuHierarchy.newWindowYn
                        , adminMenuHierarchy.level
                ) )
                .from( adminMenuHierarchy )
                .orderBy(
                        adminMenuHierarchy.hierarchy.asc()
                ).fetch();
        
        return results;
        
    }
    
    @Override
    public List<MenuListDto> getAllListAgain( MenuListDto listDto ) {
        System.out.println("캐시를 갱신할 때 사용됩니다.");
        return this.getAllList( listDto );
    }
    
    
    @Override
    public List<RoleListDto> getPossibleRoleListAjax( MenuListDto listDto ) {

//		select tmr.role_sn, tr.role_nm, tr.role_dc
//	      from tb_menu_role tmr
//	 left join tb_role  tr
//	        on tmr.role_sn = tr.role_sn
//	     where menu_sn = ?
//	  order by role_sn
        
        List<RoleListDto> results = query
                .select(
                        new QRoleListDto(
                                role.roleSn,
                                role.roleNm,
                                role.roleDc
                        )
                
                )
                .from( menuRole )
                .leftJoin( role )
                .on(
                        menuRole.roleSn.eq( role.roleSn ),
                        role.delYn.eq( "N" )
                )
                .where( eqMenuSn( listDto.getMenuSn() ) )
                .orderBy( menuRole.roleSn.asc() )
                .fetch();
        
        return results;
    }
    
    
    
    private Menu findLastUpdtDtMenu( long sn ) {
        
        Menu result = query
                // select
                .select( menu )
                .from( menu )
                .leftJoin( menuRole )
                .on(
                        menu.menuSn.eq( menuRole.menuSn ),
                        menuRole.delYn.eq( "N" )
                )
                .where( eqRoleSn( sn ),
                        menu.updtDt.isNotNull()
                )
                .orderBy(
                        menu.updtDt.desc()
                ).fetchFirst();
        return result;
    }
    
    @Override
    public Menu findLastUpdtDtMenuCache( long sn ) {
        return this.findLastUpdtDtMenu(sn);
    }
    
    @Override
    public Menu findLastUpdtDtMenuNoCache( long sn ) {
        return this.findLastUpdtDtMenu(sn);
    }
    
    @Override
    public Menu findLastUpdtDtMenuAgainCache( long sn ) {
        return this.findLastUpdtDtMenu(sn);
    }
    
    
    
    // -------------------------------- WHERE 검색 옵션 setting --------------------------------
    
    // 카테고리 lv 1 검색 옵션
    private BooleanExpression eqCateLv1( CategoryDto categoryDto ) {
        return ( categoryDto != null && categoryDto.getLv1Sn() != null
                && categoryDto.getLv1Sn() != 0 /* 0이 아닌 것은  검색 제외 */ ) ? menuRole.roleSn.eq( categoryDto.getLv1Sn() ) : null;
    }
    
    // menuSn 검색 ( upperMenuSn )
    private BooleanExpression eqMenuSn( Long sn ) {
        return ( sn != null ) ? menuRole.menuSn.eq( sn ) : null;
    }
    
    private BooleanExpression eqRoleSn( Long sn ) {
        return ( sn != null && sn != 0 ) ? menuRole.roleSn.eq( sn ) : null;
    }
    
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return menu.menuSn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return menu.menuNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
            return null;            // TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }
    
    
}

