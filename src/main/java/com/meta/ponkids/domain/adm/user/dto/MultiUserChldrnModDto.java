package com.meta.ponkids.domain.adm.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * className      : MultiUserChldrnModDto
 * author         : jjeoV
 * date           : 2023-11-19
 * description    : class of 자녀일괄 수정 Dto
 * ===========================================================
 * DATE              AUTHOR               NOTE
 * -----------------------------------------------------------
 * 2023-11-19        jjeoV             최초 생성
 */
@NoArgsConstructor
@Data
public class MultiUserChldrnModDto {
    public List<UserChldrnModDto> userChldrns;
}