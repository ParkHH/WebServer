package com.deepcore.gbridgeERP.model.service;

import java.util.List;

import org.springframework.core.io.Resource;

import com.deepcore.gbridgeERP.model.entity.File;
import com.deepcore.gbridgeERP.model.entity.FileKind;


/**
 * 
 * @author ParkHyeonho
 * File Table 에 CRUD 를 수행하기 위해 필요한 Service 객체 interface
 * Service Class 는 본 Interface 를 상속받아 CRUD 에 관련해 Repository 에 접근하는 Method Logic 을 작성한다.
 */
public interface FileService {
	public List<File> selectAllFile();					// 업로드 파일 정보 전체 조회
	public File selectFile(String fileName, int seq);	// 업로드 파일 정보 선택조회
	public List<File> selectFile(String fileKind, String CMPN_NO, String PRDT_CNMB);			// 업로드 파일 정보 선택 조회 (Overloading)
	public int insertFile(File file);					// 업로드 파일 정보 등록
	public int updateFile(String fileName, int seq);					// 업로드 파일 정보 수정
	public int deleteFile(String fileName, int seq);				// 업로드 파일 정보 삭제
	
	//---------------------------------------------
	// File Download
	//---------------------------------------------
	public Resource loadFileAsResource(String fileName, int seq);
	
	
	//---------------------------------------------
	// File KindSelect
	//---------------------------------------------
	public List<FileKind> selectAllFileKind();
}
