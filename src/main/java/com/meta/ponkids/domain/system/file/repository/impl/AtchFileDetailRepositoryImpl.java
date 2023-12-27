package com.meta.ponkids.domain.system.file.repository.impl;

import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.repository.custom.AtchFileDetailRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meta.ponkids.domain.system.file.entity.QAtchFileDetail.atchFileDetail;

@Repository
@RequiredArgsConstructor
public class AtchFileDetailRepositoryImpl implements AtchFileDetailRepositoryCustom {
    private final JPAQueryFactory query;
    
    /**
     * methodName    : getTarget
     * date           : 11/21/23
     * description    : 파일상세 단건 조회
     */
    @Override
    public AtchFileDetail getTarget( Long atchFileSn, Long fileSeq ) {
        
        return query.selectFrom( atchFileDetail )
                .where( atchFileDetail.atchFileDetailPk.atchFileSn.eq( atchFileSn ),
                        atchFileDetail.atchFileDetailPk.fileSeq.eq( fileSeq ) )
                .fetchOne();
    }
    
    /**
     * methodName    : getList
     * date           : 11/21/23
     * description    : 파일상세 리스트(다건) 조회
     */
    @Override
    public List<AtchFileDetail> getList( Long atchFileSn ) {
        return query.selectFrom( atchFileDetail )
                .where( atchFileDetail.atchFileDetailPk.atchFileSn.eq( atchFileSn ) )
                .fetch();
    }
    
    
    // 댓글 순번 + 1
    public Long maxFileSeq( Long atchFileSn ) {
        Long fileSeq = query.select( atchFileDetail.atchFileDetailPk.fileSeq.max().coalesce( ( long ) 0 ) )
                .from( atchFileDetail )
                .where(
                        atchFileDetail.atchFileDetailPk.atchFileSn.eq( atchFileSn ) )
                .fetchOne();
        
        fileSeq = fileSeq + 1;
        
        return fileSeq;
        
        
    }
    
}
