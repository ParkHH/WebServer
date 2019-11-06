package com.deepcore.gbridgeERP.model.serviceImpl;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.deepcore.gbridgeERP.exception.FileDownloadException;
import com.deepcore.gbridgeERP.model.entity.File;
import com.deepcore.gbridgeERP.model.entity.FileKind;
import com.deepcore.gbridgeERP.model.service.FileService;
import com.deepcore.gbridgeERP.repository.FileKindRepository;
import com.deepcore.gbridgeERP.repository.FileRepository;

/**
 * 
 * @author ParkHyeonho
 * Service Interface 를 상속받아 CRUD 를 직접적으로 수행하는 Repository (=DAO) 에 접근하여 작업을 수행하는 Logic 을 작성
 */
@Service
public class FileServiceImpl implements FileService {

	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	FileKindRepository fileKindRepository;
	
	private Path fileLocation = null;
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// upload file data selectAll
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public List<File> selectAllFile() {
		List<File> fileList = fileRepository.findAll();
		return fileList;
	}

	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// upload file data select
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public File selectFile(String fileName, int seq) {
		File file = fileRepository.findByFileNameAndSeq(fileName, seq);
		return file;
	}
	
	@Override
	public List<File> selectFile(String fileKind, String CMPN_NO, String IN_PRDT_CNMB) {
		List<File> fileList = fileRepository.findAll(fileKind, CMPN_NO, IN_PRDT_CNMB);
		return fileList;
	}
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// upload file data insert
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public int insertFile(File file) {
		File insert = fileRepository.save(file);
		int result = 0;
		if(insert != null) {
			result = 1;
		}else {
			result = 0;
		}
		return result;
	}

	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// upload file data update
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public int updateFile(String fileName, int seq) {
		int result = fileRepository.updateHit(fileName, seq);
		return result;
	}

	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// upload file data delete
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public int deleteFile(String fileName, int seq) {
		int result = fileRepository.deleteByFileName(fileName, seq);
		return result;
	}

	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File Download!!
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public Resource loadFileAsResource(String fileName, int seq) {
		File getFile = this.selectFile(fileName, seq);
		String fullPath = getFile.getPath()+getFile.getFileName();
		System.out.println("\n"+fullPath);
		try {
			java.io.File file = new java.io.File(fullPath);
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



	
	//---------------------------------------------------------------------------------------------------------------------------------------
	// File KindSelect
	//---------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public List<FileKind> selectAllFileKind() {
		List<FileKind> fileKindList = fileKindRepository.findAll();
		return fileKindList;
	}




	

}
