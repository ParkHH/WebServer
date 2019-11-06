package com.deepcore.gbridgeERP.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.deepcore.gbridgeERP.common.ValidateRequest;
import com.deepcore.gbridgeERP.model.entity.FileKind;
import com.deepcore.gbridgeERP.model.entity.GeneralDocument;
import com.deepcore.gbridgeERP.model.service.FileService;
import com.deepcore.gbridgeERP.model.service.GeneralDocumentService;

/**
 * 
 * @author ParkHyeonho
 * File 관련 요청 Controller
 */
@Controller
@RequestMapping(value="/admin/template")
public class FileController {	
	
	// Template 자료실 관련 Service 객체
	@Autowired
	@Qualifier("fileServiceImpl")
	FileService fileService;	
	
	
	 
	//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ Template 자료실 관련 Controller Method ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File 종류 조회!!
	// 자료실 Page 의 selectBox Option 채움
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RequestMapping("/fileKinds")
	@ResponseBody
	public String getFileKinds() {
		List<FileKind> fileKindList = fileService.selectAllFileKind();
		System.out.println("FileController > getFileKinds() > 등록된 File 종류 Domain 개수 : "+fileKindList.size());
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"fileKindList\" : [");
		for(int i=0; i<fileKindList.size(); i++) {
			FileKind fileKind = fileKindList.get(i);
			sb.append("{");
			sb.append("\"CD_VLID_VAL\":\""+fileKind.getCD_VLID_VAL()+"\",");
			sb.append("\"CD_VLID_VAL_DESC\":\""+fileKind.getCD_VLID_VAL_DESC()+"\"");
			if(i<fileKindList.size()-1) {
				sb.append("},");
			}else {
				sb.append("}");
			}
		}
		sb.append("]");
		sb.append("}");
			
		return sb.toString();
	}
			
	
		
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File Upload!!
	// File 을 Server 의 Directory 경로에 저장!
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@PostMapping(value="/upload")
	public ResponseEntity<String> uploadTemplate(String uploader, String file_kind, String CMPN_NO, String PRDT_CNMB, @RequestParam("FileParam") MultipartFile fileData) {
		System.out.println("ERP Template Upload 요청이 들어왔습니다.");
		//System.out.println("넘어온 Parameter\n---------------------- \nfile : "+file+"\ncompany : "+company+"\nproduct : "+product+"\n----------------------");			
		ResponseEntity<String> result = null;
		
		
		// File Rename
		//************************************************************************************
		String originFileName = fileData.getOriginalFilename();
		//System.out.println("originFileName : "+originFileName);
		int dotIndex = originFileName.lastIndexOf('.');
		String exrt = originFileName.substring(dotIndex, originFileName.length());
		//System.out.println("File 확장자 이름 : "+exrt);
		String newFileName = file_kind+"_"+CMPN_NO+"_"+PRDT_CNMB+exrt;
		//************************************************************************************
		
		if(ValidateRequest.validateRequestParameter(file_kind, CMPN_NO, PRDT_CNMB)) {		// 넘어온 Parameter 값들이 존재하는 값들인지 유효성 검사				
			try {				
				String subDirectory = "";
				if(file_kind.equals("V")) {
					subDirectory="Voucher";
				}else if(file_kind.equals("C")) {
					subDirectory = "Consumer";
				}else if(file_kind.equals("P")) {
					subDirectory = "Product";
				}else if(file_kind.equals("S")) {
					subDirectory = "Stage";
				}
				byte[] bytes = fileData.getBytes();									// Parameter 로 넘어온 File Byte 정보 가져옴
				String savePath = "/home/gbridge/template/"+subDirectory+"/";			// File 이 저장될 경로
				File saveDir = new File(savePath);									// 경로에 위치하는 Directory 를 Instance 화 한다.
				if(!saveDir.exists()) {												// 만약 지정 경로로 생성된 File Instance 가 존재하지 않으면
					saveDir.mkdirs();												// 해당 경로를 Local 에 생성! (Directory 생성)
				}
				Path path = Paths.get(savePath, newFileName);						// File 저장 경로와 저장될 File 이름을 지정하여 Path Instance 생성			
				Files.write(path, bytes);											// 지정한 저장 정보로 File Byte 정보 저장!	
				
				
				// Upload 성공하면 DB 에 등록!!
				//	- write 실패시 Exception 발생해서 catch 문으로 넘어감
				//	- 즉 아래 Logic 은 실행 안되게 된다.
				//******************************************************************************************
				com.deepcore.gbridgeERP.model.entity.File fileDto = new com.deepcore.gbridgeERP.model.entity.File();
				fileDto.setFileKind(file_kind);
				fileDto.setCMPN_NO(CMPN_NO);
				fileDto.setPRDT_CNMB(Integer.parseInt(PRDT_CNMB));
				fileDto.setFileName(newFileName);
				fileDto.setPath(savePath);
				fileDto.setUploader(uploader);
				fileService.insertFile(fileDto);
				//******************************************************************************************
				
				result = new ResponseEntity<String>(HttpStatus.OK);
			}catch (Exception e) {
				e.printStackTrace();
				result = new ResponseEntity<String>(HttpStatus.FAILED_DEPENDENCY);
			}
		}
		return result;
	}
	
		
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File SelectAll!!
	// Upload 된 File 목록 전체를 조회한다!
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@GetMapping(value="/all")
	@ResponseBody
	public String selectAllTemplate(){
		System.out.println("모든 Template File 목록을 조회합니다.");
		List<com.deepcore.gbridgeERP.model.entity.File> fileList = fileService.selectAllFile();
		int fileCount = fileList.size();

		System.out.println("FileController : selectAllTemplate() : 조회 Record 건수 : "+fileCount);
		
		
		//******************************************************************************
		// 조회 Data 를 JSON 문자열로 만들기!
		//******************************************************************************
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"fileList\":[");
		for(int i=0; i<fileCount; i++) {
			com.deepcore.gbridgeERP.model.entity.File file = fileList.get(i);
			sb.append("{");
			sb.append("\"seq\":"+file.getSeq_file()+",");
			sb.append("\"file_kind\":\""+file.getFileKind()+"\",");
			sb.append("\"CMPN_NO\":\""+file.getCMPN_NO()+"\",");
			sb.append("\"PRDT_CNMB\":"+file.getPRDT_CNMB()+",");
			sb.append("\"file_name\" : \""+file.getFileName()+"\",");
			sb.append("\"path\" : \""+file.getPath()+"\",");
			sb.append("\"uploader\" : \""+file.getUploader()+"\",");
			sb.append("\"date\" : \""+file.getDate().substring(0,10)+"\",");
			sb.append("\"hit\" : "+file.getHit());
			if(i<fileCount-1) {
				sb.append("},");
			}else {
				sb.append("}");
			}			
		}
		sb.append("]");
		sb.append("}");
		//System.out.println(sb.toString());

		return sb.toString();
	}
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File Download!!
	// File Download 진행!!
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile(String file_name, String seq, HttpServletRequest request) {			
		ResponseEntity<Resource> result = null;
		String fileName = file_name;
		int seq_file = Integer.parseInt(seq);
		//System.out.println("\n>>>>>>>>>>>넘어온 Parameter\n1.file_name : "+fileName+"\n2.seq : "+seq+"\n");
		com.deepcore.gbridgeERP.model.entity.File file = fileService.selectFile(fileName, seq_file);
		//System.out.println("\n>>>>>>>조회한 File 정보\n1.file_name:"+file.getFileName()+"\n2.path:"+file.getPath()+"\n3.uploader:"+file.getUploader()+"\n4.date:"+file.getDate()+"\n");
		int updateHitResult = fileService.updateFile(file_name, seq_file);
		if(updateHitResult == 0) {
			return result;
		}else {
			//******************************************************************************************************
			// File 보내기!
			//******************************************************************************************************
			Resource resource = fileService.loadFileAsResource(fileName, seq_file);
			
			//***********************************
			// 전송할 File 의 MIME Type 을 구함
			//***********************************
			String contentType = null;
			String encodedFileName = null;
	        try {
	        	
	        	//**********************************************************************
	        	// File 전송시 File 의 TYPE 에 해당하는 MIME Type 을 구함
	        	//**********************************************************************
	            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	            //encodedFileName = URLEncoder.encode(resource.getFilename(), "UTF-8");
	            encodedFileName = resource.getFilename();
	        } catch (IOException ex) {
	            //logger.info("Could not determine file type.");
	        }
	        
	        //**********************************************************************
	        // 만약 File 의 MIME Type 이 존재하지 않는다면
	        //**********************************************************************
	        if(contentType == null) {
	        	// File 전송시 File 의 Type 을 모두 포함하는 MIME TYPE
	            contentType = "application/octet-stream";	
	        }
	        result = ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
	                .body(resource);
	        return result; 
		}
	}
			
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File Select!!ㅣ
	// Upload 된 File 목록을 검색조건에 맞게 조회!
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@GetMapping("/search")
	@ResponseBody
	public String selectTemplate(String fileKind, String CMPN_NO, String PRDT_CNMB) {
		System.out.println("FileController > selectTemplate() > Parameter : "+fileKind+","+CMPN_NO+","+PRDT_CNMB);
		String result = "";		
		List<com.deepcore.gbridgeERP.model.entity.File> fileList = null;
		if(fileKind == null && CMPN_NO == null && PRDT_CNMB == null) {
			fileService.selectAllFile();
		}else {
			fileList = fileService.selectFile(fileKind, CMPN_NO, PRDT_CNMB);
		}
		int fileCount = fileList.size();
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"fileList\":[");
		for(int i=0; i<fileCount; i++) {
			com.deepcore.gbridgeERP.model.entity.File file = fileList.get(i);
			sb.append("{");
			sb.append("\"seq\":"+file.getSeq_file()+",");
			sb.append("\"file_kind\":\""+file.getFileKind()+"\",");
			sb.append("\"CMPN_NO\":\""+file.getCMPN_NO()+"\",");
			sb.append("\"PRDT_CNMB\":"+file.getPRDT_CNMB()+",");
			sb.append("\"file_name\":\""+file.getFileName()+"\",");
			sb.append("\"path\":\""+file.getPath()+"\",");
			sb.append("\"uploader\":\""+file.getUploader()+"\",");
			sb.append("\"date\" : \""+file.getDate().substring(0,10)+"\",");
			sb.append("\"hit\" : "+file.getHit());
			if(i<fileCount-1) {
				sb.append("},");
			}else {
				sb.append("}");
			}			
		}
		sb.append("]");
		sb.append("}");
		//System.out.println(sb.toString());
		
		return sb.toString();
	}
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File Delete!!
	// Upload 된 File 목록중 삭제버튼을 Click 한 Upload File 이력 지우기!!
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	@ResponseBody
	public String deleteTemplate(String fileName, int seq) {
		JSONObject json = new JSONObject();
		com.deepcore.gbridgeERP.model.entity.File file = fileService.selectFile(fileName, seq);
		if(file == null) {
			json.put("resultCode", -1);
			System.out.println("DB 에 파일 정보가 존재하지 않음!");			
			//return json.toString();
		}
		System.out.println("파일 존재함!");
		String deletePath = file.getPath()+file.getFileName();
		System.out.println("삭제할 파일 Path : "+deletePath);
		File deleteFile = new File(deletePath);
		if(!deleteFile.delete()) {
			json.put("resultCode", -2);
			System.out.println("서버 디스크에 파일이 존재하지 않음!");			
			//return json.toString();
		}
		System.out.println("서버 디스크 파일 삭제 완료!");
		int deleteResultFromDB = fileService.deleteFile(fileName, seq);
		json.put("resultCode", deleteResultFromDB);	
		System.out.println("서버 DB Data 삭제 완료!");		
		
		return json.toString();
	}
	
}












