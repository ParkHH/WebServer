package com.deepcore.gbridgeERP.model.serviceImpl;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepcore.gbridgeERP.exception.FileDownloadException;
import com.deepcore.gbridgeERP.model.entity.GeneralDocument;
import com.deepcore.gbridgeERP.model.service.GeneralDocumentService;
import com.deepcore.gbridgeERP.repository.GeneralDocumentRepository;

/**
 * 
 * @author ParkHyeonho
 * 일반 문서 자료실 관련 Service 객체
 */

@Service
public class GeneralDocumentServiceImpl implements GeneralDocumentService {
	
	@Autowired
	GeneralDocumentRepository generalDocumentRepository;
	
	//-----------------------------------------------------------------------------------------------------------------------------
	// selectAll GeneralDocuments
	//-----------------------------------------------------------------------------------------------------------------------------
	@Override
	public List<GeneralDocument> selectAllGeneralDocument() {
		List<GeneralDocument> generalDocumentList = generalDocumentRepository.findAll();
		return generalDocumentList;
	}

	
	
	
	
	
	//-----------------------------------------------------------------------------------------------------------------------------
	// select the GeneralDocument
	//-----------------------------------------------------------------------------------------------------------------------------
	@Override
	public GeneralDocument selectGeneralDocument(String fileName, int seq) {
		GeneralDocument generalDocument = generalDocumentRepository.searchGeneralDocument(fileName, seq);
		return generalDocument;
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------
	// select the GeneralDocument (검색) - Overloading
	//-----------------------------------------------------------------------------------------------------------------------------
	@Override
	public List<GeneralDocument> selectGeneralDocument(String searchConditionValue, String searchWord) {
		List<GeneralDocument> generalDocumentList = generalDocumentRepository.searchGeneralDocument(searchConditionValue, searchWord);
		return generalDocumentList;
	}
	
		
	
	
	
	
	//-----------------------------------------------------------------------------------------------------------------------------
	// delete the GeneralDocument
	//-----------------------------------------------------------------------------------------------------------------------------
	@Transactional
	@Override
	public int deleteGeneralDocument(String fileName, int seq) {
		int deleteResult = 0;
		GeneralDocument file = generalDocumentRepository.searchGeneralDocument(fileName, seq);		// 삭제할 File 정보 DB 에서 찾기
		if(file != null) {																			// Data 가 존재한다면
			 File delFile = new File(file.getPATH());												// Path 를 활용하여 File Instance 를 만들고
			 delFile.delete();																		// PC Disk 에서의 File 삭제를 진행
			deleteResult = generalDocumentRepository.deleteGeneralDocument(fileName, seq);			// 이후 DB 에서 file upload 정보를 삭제
		}
		return deleteResult;																		// 결과 반환
	}
	
	
	
	
	
	//-----------------------------------------------------------------------------------------------------------------------------
	// insert the GeneralDocument
	//-----------------------------------------------------------------------------------------------------------------------------
	@Override
	public int insertGeneralDocument(GeneralDocument generalDocument) {
		String FILE_NAME = generalDocument.getFILE_NAME();
		String PATH = generalDocument.getPATH();
		String UPLOADER = generalDocument.getUPLOADER();
		String DSCPT = generalDocument.getDSCPT();
		
		int insertResult = generalDocumentRepository.insertGeneralDocument(FILE_NAME, PATH, UPLOADER, DSCPT);
		
		return insertResult;
	}

	
	
	
	
	
	
	//-----------------------------------------------------------------------------------------------------------------------------
	// update the GeneralDocument (파일 미포함)
	//-----------------------------------------------------------------------------------------------------------------------------
	@Override
	public int updateGeneralDocument(String fileName, int seq, String description) {
		int updateResult = generalDocumentRepository.updateGeneralDocument(fileName, description, seq);
		return updateResult;
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------
	// update the GeneralDocument (파일 포함) - Overloading
	//-----------------------------------------------------------------------------------------------------------------------------
	@Override
	public int updateGeneralDocument(GeneralDocument generalDocument) {
		int SEQ = generalDocument.getSEQ();
		String FILE_NAME = generalDocument.getFILE_NAME();
		String PATH = generalDocument.getPATH();
		String UPLOADER = generalDocument.getUPLOADER();
		String DSCPT = generalDocument.getDSCPT();
		
		int updateResult = generalDocumentRepository.updateGeneralDocument(FILE_NAME, DSCPT, PATH, SEQ);
		
		return updateResult;
	}	

	
	
	
	
	
	//-----------------------------------------------------------------------------------------------------------------------------
	// GeneralDocument Download
	//-----------------------------------------------------------------------------------------------------------------------------
	@Override
	public Resource loadGeneralDocumentAsResource(String fileName, int seq) {
		GeneralDocument getFile = this.selectGeneralDocument(fileName, seq);
		String fullPath = getFile.getPATH();
		System.out.println("\n"+fullPath);
		try {
			File file = new File(fullPath);
            Path filePath = file.toPath();
            Resource resource = new UrlResource(filePath.toUri());
            
            if(resource.exists()) {
                return resource;
            }else {
                throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
            }
        }catch(MalformedURLException e) {
            throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
  
        }
		
	}





	//-----------------------------------------------------------------------------------------------------------------------------
	// GeneralDocument update (increase)  hit
	//-----------------------------------------------------------------------------------------------------------------------------
	@Override
	public int updateHit(String fileName, int seq) {
		int updateResult = generalDocumentRepository.updateHitGeneralDocument(fileName, seq);
		return updateResult;
	}


	
}
