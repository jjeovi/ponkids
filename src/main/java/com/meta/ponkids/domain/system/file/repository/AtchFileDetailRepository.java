package com.meta.ponkids.domain.system.file.repository;

import com.meta.ponkids.domain.system.file.entity.AtchFile;
import com.meta.ponkids.domain.system.file.entity.AtchFileDetail;
import com.meta.ponkids.domain.system.file.entity.pk.AtchFileDetailPk;

import org.springframework.data.jpa.repository.JpaRepository;

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
public interface AtchFileDetailRepository extends JpaRepository<AtchFileDetail, AtchFileDetailPk> {

}
