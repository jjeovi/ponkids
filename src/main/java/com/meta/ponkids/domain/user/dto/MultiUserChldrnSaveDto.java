package com.meta.ponkids.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * className      : MultiUserChldrnSaveDto
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 자녀일괄등록 Dto
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@NoArgsConstructor
@Data
public class MultiUserChldrnSaveDto {
    public List<UserChldrnSaveDto> userChldrns;
}