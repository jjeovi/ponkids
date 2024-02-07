package com.meta.ponkids.global.util.error;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.meta.ponkids.global.util.file.FileUtils;
import com.meta.ponkids.global.util.file.dto.JsonDto;

@Component
public class ErrorUtils {
	
    public static String JSON_FILE_PATH;

    @Value("${key.cmmnCd.jsonFilePath}")
    public void setJsonFilePath(String value) {
    	JSON_FILE_PATH = value;
    }
	
    
    public static String ERR_CD;
    
    @Value("ERR_CD")
    public void setErrCd(String value) {
    	ERR_CD = value;
    }
    
    
    public static String DIVIDER;
    
    @Value("${key.divider}")
    public void setDivider(String value) {
    	DIVIDER = value;
    }

    @SuppressWarnings("unchecked")
	public static String getErrorMessage( String errCd ) throws FileNotFoundException {
    	
    	// file read
		String path = System.getProperty( "user.dir" ) + JSON_FILE_PATH + ERR_CD;	// rootPath(System.getProperty("user.dir"))부터 path 설정
		String fileName = ERR_CD;
		List<JsonDto> errorCdList = (List<JsonDto> ) FileUtils.readJsonFile( path + DIVIDER , fileName );
		
		Optional<JsonDto> errCdDto =  errorCdList.stream().filter( f -> f.getCdDetailNm().equals(errCd) ).findAny();
		
		String resultMsg = "";
		
		if ( errCdDto.isPresent() ) {
			resultMsg =  errCdDto.get().getCdDetailVal1();					// 에러 메시지 : cdDetailVal 1 에 저장.
		}else {
			resultMsg = "에러가 발생하였습니다. 다시 시도해 주세요.(NFDEMCD000)";		// String errCd 값이 에러목록에 없을 때. Not Found Error 000
		}
		
		return resultMsg;
	}

}
