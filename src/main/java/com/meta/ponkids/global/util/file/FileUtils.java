package com.meta.ponkids.global.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meta.ponkids.domain.system.cmmnCd.dto.CmmnCdDetailListDto;
import com.meta.ponkids.global.util.file.dto.JsonDto;

public class FileUtils {
	
	
	// 경로 생성 ( 폴더 생성 )  
	public static void createPath( String path ) {
		
		Path directoryPath = Paths.get( path );		 
		
		try {			
			
			// 디렉토리 생성			
			Files.createDirectory(directoryPath);			
			System.out.println(directoryPath + " 디렉토리가 생성되었습니다.");		
			
			
		} catch (FileAlreadyExistsException e) {			
		
			// Nothing
			
		} catch (NoSuchFileException e) {		
			
			System.out.println("디렉토리 경로가 존재하지 않습니다");		
			
		}catch (IOException e) {		
			
			e.printStackTrace();		
			
		}
	}
	
	
	// json 파일 생성
	public static void createJsonFile(String path, String fileName, Object fileData) throws IOException {

		// Gson 사용
		Gson gson = new Gson();
		
		String isExistFile_Path = path + fileName + ".json";	// 파일이 실제 존재하는지 체크하기 위한 경로 : String 변수
		isExistFile_Path = isExistFile_Path.replaceAll( "\\\\", "/" );
		
		File file = new File( isExistFile_Path );
		
		Path existPath = Paths.get( path +  fileName + ".json" );
		Path oldPath = Paths.get( path +  fileName + "_old.json" );
		
		if ( file.exists() ) {
			// 파일이 존재하면 old 버전으로 복사
			Files.copy( existPath, oldPath, StandardCopyOption.REPLACE_EXISTING ); // oldPath 가 존재한다 하더라도 덮어쓰기로 old 파일 생성
		}
		
		// 파일이 존재하지 않다면 : 새로 생성
		try ( PrintWriter out = new PrintWriter( new FileWriter( path + fileName + ".json" ) ) ) {
			String jsonString = gson.toJson( fileData );
			out.write( jsonString );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	
	// json 파일 읽기
	public static Object readJsonFile( String path, String fileName ) throws FileNotFoundException {
		
		
		String isExistFile_Path = path + fileName + ".json";	// 파일이 실제 존재하는지 체크하기 위한 경로 : String 변수
		isExistFile_Path = isExistFile_Path.replaceAll( "\\\\", "/" );
		
	     // FileReader 생성        
		Reader reader = new FileReader( isExistFile_Path );         
		
		Type listType = new TypeToken<ArrayList<JsonDto>>() {}.getType();
		
		// Json 파일 읽어서, Lecture 객체로 변환        
		Gson gson = new Gson();        
		Object object = gson.fromJson(reader, listType);
		
		
		return object;
	}
	
	
		

}
