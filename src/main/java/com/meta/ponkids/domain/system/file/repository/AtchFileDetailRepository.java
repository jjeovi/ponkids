package com.meta.ponkids.domain.system.file.repository;

import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.entity.pk.AtchFileDetailPk;
import com.meta.ponkids.domain.system.file.repository.custom.AtchFileDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * InterfaceName  : AtchFileDetailRepository
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : interface of 파일 Repository
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
public interface AtchFileDetailRepository extends JpaRepository<AtchFileDetail, AtchFileDetailPk>, AtchFileDetailRepositoryCustom {
    
    
    int deleteByAtchFileDetailPk_AtchFileSn( Long acthFileSn );
    
    Long maxFileSeq( Long acthFileSn );
    
    
    @Modifying( clearAutomatically = true )
    @Query( value = "DELETE FROM {h-schema}tb_atch_file_detail "
            + "    WHERE atch_file_sn = :acthFileSn AND file_seq = :fileSeq", nativeQuery = true )
        // nativeQuery true 없으면 error
    int deleteByAtchFileDetailPk( @Param( "acthFileSn" ) Long acthFileSn, @Param( "fileSeq" ) Long fileSeq );
    
    
}
