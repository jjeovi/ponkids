package com.meta.ponkids.domain.system.login.repository.impl;

import com.meta.ponkids.domain.system.login.dto.LoginDto;
import com.meta.ponkids.domain.system.login.dto.QLoginDto;
import com.meta.ponkids.domain.system.login.repository.custom.LoginRepositoryCustom;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.meta.ponkids.domain.cls.entity.QClass.class$;
import static com.meta.ponkids.domain.system.role.entity.QRole.role;
import static com.meta.ponkids.domain.user.entity.QUser.user;
import static com.meta.ponkids.domain.user.entity.QUserRole.userRole;

@Repository
@RequiredArgsConstructor
public class LoginRepositoryImpl implements LoginRepositoryCustom {
    private final JPAQueryFactory query;
    
    @Override
    public LoginDto getLogin( String userId ) {

//        select tu.user_sn
//                , tu.user_id
//                , tu."password"
//                , tu.user_nm
//                , tur.role_sn
//                , tr.role_nm
//                , tr.role_dc
//                , tu.gender
//                , tu.brdt_date
//                , tu.tel_no
//                , tu.reside_area
//                , tu.zip
//                , tu.rdnm_adr
//                , tu.detail_adr
//                , tu.atch_file_sn
//                , tu.mngr_yn
//                , tu.mngr_confm_yn
//                , tu.confmer_id
//                , tu.confm_dt
//                , tu.cntn_sns
//                , tu.last_login_dt
//        from tb_user tu
//        left join tb_user_role tur
//        on tu.user_sn = tur.user_sn
//        and tur.del_yn = 'N'
//        left join tb_role tr
//        on tur.role_sn = tr.role_sn
//        and tr.del_yn = 'N'
//        where tu.del_yn = 'N'
//        and tu.user_id = 'admin@test.com';
        
        // (1) 결과list (results).
        LoginDto result = query
                // select
                .select( new QLoginDto(
                                user.userSn,
                                user.userId,
                                user.password,
                                role.roleSn,
                                role.roleNm,
                                role.roleDc,
                                user.userNm,
                                user.gender,
                                user.brdtDate,
                                user.telNo,
                                user.resideArea,
                                user.atchFileSn,
                                user.mngrYn,
                                user.mngrConfmYn,
                                user.confmerId,
                                user.confmerIp,
                                user.confmDt,
                                user.snsKakaoCntnYn,
                                Expressions.stringTemplate( "to_char({0}, '{1s}')", user.snsKakaoCntnDt, "YYYY-MM-DD HH24:MI:SS" ),
                                user.snsGoogleCntnYn,
                                Expressions.stringTemplate( "to_char({0}, '{1s}')", user.snsGoogleCntnDt, "YYYY-MM-DD HH24:MI:SS" ),
                                user.snsNaverCntnYn,
                                Expressions.stringTemplate( "to_char({0}, '{1s}')", user.snsNaverCntnDt, "YYYY-MM-DD HH24:MI:SS" ),
                                user.snsFacebookCntnYn,
                                Expressions.stringTemplate( "to_char({0}, '{1s}')", user.snsFacebookCntnDt, "YYYY-MM-DD HH24:MI:SS" ),
                                user.snsAppleCntnYn,
                                Expressions.stringTemplate( "to_char({0}, '{1s}')", user.snsAppleCntnDt, "YYYY-MM-DD HH24:MI:SS" ),
                                Expressions.stringTemplate( "to_char({0}, '{1s}')", user.lastLoginDt, "YYYY-MM-DD HH24:MI:SS" )
                        )
                )
                // from  ( ~left join .. )
                .from( user )
                .leftJoin( userRole )
                .on( userRole.userSn.eq( user.userSn ),
                        userRole.delYn.eq( "N" ) )
                .leftJoin( role )
                .on( role.roleSn.eq( userRole.roleSn ),
                        role.delYn.eq( "N" ) )
                // where
                .where( user.userId.eq( userId ) )
                .fetchFirst();
        
        return result;
    }
    
}
