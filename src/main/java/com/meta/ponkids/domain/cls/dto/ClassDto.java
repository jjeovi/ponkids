package com.meta.ponkids.domain.cls.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ClassDto {
	
	private Long classSn;                   // 클래스일련번호
    
	private List<String> 					classWeek;          // 요일
    
    private List<ClassDetailSaveDto>		classDetails;		// 입력 항목
    
    private List<ClassDetailOptnSaveDto>	classDetailOptns;	// 입력 항목 ( 선택형시 : 추후 개발 예정)
}
