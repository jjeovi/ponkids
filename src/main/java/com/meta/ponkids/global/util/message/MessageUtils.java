package com.meta.ponkids.global.util.message;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import com.meta.ponkids.global.util.file.FileUtils;
import com.meta.ponkids.global.util.file.dto.JsonDto;

@Component
public class MessageUtils {

    public static String JSON_FILE_PATH;

    @Value("${key.cmmnCd.jsonFilePath}")
    public void setJsonFilePath(String value) {
    	JSON_FILE_PATH = value;
    }
	
    public static String DIVIDER;
    
    @Value("${key.divider}")
    public void setDivider(String value) {
    	DIVIDER = value;
    }

    @SuppressWarnings("unchecked")
	public static String getMessageFromCmmnCd( String cdNm,  String cdDetailNm )  {
    	
    	// file read
		String path = System.getProperty( "user.dir" ) + JSON_FILE_PATH + cdNm;	// rootPath(System.getProperty("user.dir"))부터 path 설정
		String fileName = cdNm;
		List<JsonDto> messageList = null;
		try {
			messageList = (List<JsonDto> ) FileUtils.readJsonFile( path + DIVIDER , fileName );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String resultMsg = "";
		
		
		
		if ( messageList != null ) {
			Optional<JsonDto> messageDto =  messageList.stream().filter( f -> f.getCdDetailNm().equals(cdDetailNm) ).findAny();
			
			if ( messageDto.isPresent() ) {
				resultMsg =  messageDto.get().getCdDetailVal1();					// 에러 메시지 : cdDetailVal 1 에 저장.
			}else {
				resultMsg = "에러가 발생하였습니다. 다시 시도해 주세요.(NFDEMCD000)";		// String cdDetailNm 값이 메시지 목록에 없을 때. Not Found Error Message 000
			}
			
		} else {
			resultMsg = "에러가 발생하였습니다. 다시 시도해 주세요.(NFDEMCD000)";		// String cdDetailNm 값이 메시지 목록에 없을 때. Not Found Error Message 000
			return resultMsg;
		}
		
		
		
		

		return resultMsg;
	}

}
