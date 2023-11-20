package com.meta.ponkids.domain.system.file.repository;

import com.meta.ponkids.domain.system.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * InterfaceName  : FileRepository
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : interface of 파일 Repository
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
public interface FileRepository extends JpaRepository<File, Long> {

}
