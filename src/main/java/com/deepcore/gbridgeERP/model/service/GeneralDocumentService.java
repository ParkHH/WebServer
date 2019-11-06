package com.deepcore.gbridgeERP.model.service;

import java.util.List;

import org.springframework.core.io.Resource;

import com.deepcore.gbridgeERP.model.entity.File;
import com.deepcore.gbridgeERP.model.entity.GeneralDocument;


/**
 * 
 * @author ParkHyeonho
 * File Table 에 CRUD 를 수행하기 위해 필요한 Service 객체 interface
 * Service Class 는 본 Interface 를 상속받아 CRUD 에 관련해 Repository 에 접근하는 Method Logic 을 작성한다.
 */
public interface GeneralDocumentService {
	public List<GeneralDocument> selectAllGeneralDocument();													// 업로드 파일 정보 전체 조회
	public GeneralDocument selectGeneralDocument(String fileName, int seq);										// 업로드 파일 정보 선택 조회
	public List<GeneralDocument> selectGeneralDocument(String searchConditionValue, String searchWord);			// 업로드 파일 정보 선택 조회
	public int insertGeneralDocument(GeneralDocument generalDocument);											// 업로드 파일 정보 등록
	public int updateGeneralDocument(String fileName, int seq, String dscpt);									// 업로드 파일 정보 수정 (파일 미포함)
	public int updateGeneralDocument(GeneralDocument generalDocument);											// 업로드 파일 정보 수정 (파일 포함)
	public int deleteGeneralDocument(String fileName, int seq);													// 업로드 파일 정보 삭제
	
	//----------------------------------------------
	// updateHit
	//----------------------------------------------
	public int updateHit(String fileName, int seq);
	
	//---------------------------------------------
	// File Download
	//---------------------------------------------
	public Resource loadGeneralDocumentAsResource(String fileName, int seq);
}
