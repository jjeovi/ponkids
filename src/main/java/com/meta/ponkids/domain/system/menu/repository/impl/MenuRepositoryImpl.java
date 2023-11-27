package com.meta.ponkids.domain.system.menu.repository.impl;


import com.meta.ponkids.domain.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.system.menu.dto.QMenuListDto;
import com.meta.ponkids.domain.system.menu.repository.custom.MenuRepositoryCustom;
import com.meta.ponkids.global.common.dto.CategoryDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.meta.ponkids.domain.system.menu.entity.QMenuHierarchy.menuHierarchy;
import static com.meta.ponkids.domain.system.menu.entity.QMenuRole.menuRole;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepositoryCustom {
	
	private final JPAQueryFactory query;
	
	@Override
	public List<MenuListDto> getList( MenuListDto listDto ) {
		
		// TODO 구현
		// (1) '결과list' 와 (2)'count' 를 2번에 걸쳐 조회
		
	//		select tmr.menu_sn
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
	//		from tb_menu_role tmr
	//		left join vw_menu_hierarchy vmh
	//		on tmr.menu_sn = vmh.menu_sn
	//		where tmr.role_sn = '100002';
		
        // (1) 결과list (results).
		List<MenuListDto> results = query
				// select
                .select( new QMenuListDto(
                		  menuRole.menuSn
						, menuRole.roleSn
						, menuHierarchy.upperMenuSn
						, menuHierarchy.menuNm
						, menuHierarchy.menuPath
						, menuHierarchy.hierarchy
						, menuHierarchy.requiredMenu
						, menuHierarchy.menuCd
						, menuHierarchy.menuUrl
						, menuHierarchy.parntsMenuYn
						, menuHierarchy.menuSeq
						, menuHierarchy.menuDcSetYn
						, menuHierarchy.menuDc
						, menuHierarchy.menuDetailDc
						, menuHierarchy.atchFileSn
						, menuHierarchy.useYn
						, menuHierarchy.newWindowYn
//                		new CaseBuilder()
//                		.when( user.gender.eq("M")).then("남자")
//                		.when( user.gender.eq("F")).then("여자")
//                		.otherwise("")
//                		.as("gender"),
                		) )					
                .from( menuRole )
				.leftJoin( menuHierarchy )	// view : vw_menu_hierarchy
				.on( menuRole.menuSn.eq( menuHierarchy.menuSn ) )
                .where(
						eqCateLv1( listDto.getCategory() )
				)
                .fetch();
		
		// (2) count
//        JPAQuery<Long> count = query.select( menu.count() )
//                .from( menu )
//                .where(
//                        eqOption( listDto.getSchOption(), listDto.getSchCntn() ) );
		
		return results;
		
	}
	
	// -------------------------------- WHERE 검색 옵션 setting --------------------------------
	
	// 카테고리 lv 1 검색 옵션
	private BooleanExpression eqCateLv1( CategoryDto categoryDto ) {
		return (categoryDto != null && categoryDto.getLv1Sn() != null && categoryDto.getLv1Sn() != 0 )   ? menuRole.roleSn.eq( categoryDto.getLv1Sn() ) : null;
	}
	
	
    private BooleanExpression eqOption( String schOption, String schCntn ) {
        // 검색 옵션  A : 아이디 , B : 이름 <- 예시 일뿐 이런식으로 커스텀하면 됨
        if ( StringUtils.hasText( schOption ) && StringUtils.hasText( schCntn ) ) {
//            if ( schOption.equals( "A" ) )
//                return menu.menuSn.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else if ( schOption.equals( "B" ) )
//                return menu.menuNm.contains( schCntn ); // TODO LIKE검색. contains.( schCntn ) == LIKE '%' || schCntn || '%'
//            else return null;
        	return null;			// TODO (build한 이후에 해주세요. 안그럼 에러발생)  실제 구현시에는 해당부분지워주고 위에부분주석풀기
        } else {
            return null;
        }
    }

}

