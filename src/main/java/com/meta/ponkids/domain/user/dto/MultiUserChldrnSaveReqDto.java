package com.meta.ponkids.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MultiUserChldrnSaveReqDto {
    public List<UserChldrnSaveReqDto> userChldrns;
}