package com.deepcore.gbridgeERP.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.naming.directory.ModificationItem;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.deepcore.gbridgeERP.model.entity.GeneralDocument;
import com.deepcore.gbridgeERP.model.service.GeneralDocumentService;

import net.bytebuddy.matcher.ModifierMatcher.Mode;

/**
 * 
 * @author ParkHyeonho
 * 일반 문서자료실 관련 요청 처리 Controller
 */

@Controller()
@RequestMapping("/admin/generalDocument")
public class GeneralDocumentController {
	
	// 일반 문서 자료실 관련 Service 객체
	@Autowired
	@Qualifier("generalDocumentServiceImpl")
	GeneralDocumentService generalDocumentService;
	
	
	//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 일반 문서 자료실 관련 Controller Method ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File 등록 Page 로 이동!!
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RequestMapping("/registPage")
	public String goGeneralDocumentRegistPage() {
		System.out.println("\nGeneralDocumentController > goGeneralDocumentRegistPage > 요청 수신");
		return "board/generalDocumentRegistPage.html";
	}
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File 전체 목록 조회!!
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/all", method=RequestMethod.GET)
	@ResponseBody
	public String selectAllGeneralDocument() {
		System.out.println("\nGeneralDocumentController > selectAllGeneralDocument : 요청 수신");
		List<GeneralDocument> generalDocumentList = generalDocumentService.selectAllGeneralDocument();
		System.out.println("GeneralDocumentController > selectAllGeneralDocument() : generalDocumentList 길이 : "+generalDocumentList.size());
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"generalDocumentList\" : [");
		for(int i=0; i<generalDocumentList.size(); i++) {
			GeneralDocument generalDocument = generalDocumentList.get(i);
			sb.append("{");
			sb.append("\"seq\":"+generalDocument.getSEQ()+",");
			sb.append("\"file_name\":\""+generalDocument.getFILE_NAME()+"\",");
			sb.append("\"path\":\""+generalDocument.getPATH()+"\",");
			sb.append("\"date\":\""+generalDocument.getDATE()+"\",");
			sb.append("\"uploader\":\""+generalDocument.getUPLOADER()+"\",");
			sb.append("\"hit\":"+generalDocument.getHIT());
			if(i<generalDocumentList.size()-1) {
				sb.append("},");
			}else {
				sb.append("}");
			}
		}
		sb.append("]");
		sb.append("}");
		System.out.println("GeneralDocumentController > selectAllGeneralDocument() : 전송되는 JSON : "+sb.toString());
		
		return sb.toString();
	}
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File 조회 (검색)!!
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/search", method=RequestMethod.GET)
	@ResponseBody
	public String selectGeneralDocument(String searchConditionValue, String searchWord) {
		System.out.println("\nGeneralDocumentController > selectGeneralDocument > 요청 수신");
		/*
		 * ********************************
		 * searchConditionValue
		 * ********************************
		 * 1) '' 	: 전체 검색
		 * 2) '1'	: 작성자 검색
		 * 3) '2'	: 글제목 검색
		 * 4) '3'	: 작성자+글제목 검색
		 */
		
		List<GeneralDocument> generalDocumentList = generalDocumentService.selectGeneralDocument(searchConditionValue, searchWord);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"generalDocumentList\" : [");
		for(int i=0; i<generalDocumentList.size(); i++) {
			GeneralDocument generalDocument = generalDocumentList.get(i);
			sb.append("{");
			sb.append("\"seq\":"+generalDocument.getSEQ()+",");
			sb.append("\"file_name\":\""+generalDocument.getFILE_NAME()+"\",");
			sb.append("\"path\":\""+generalDocument.getPATH()+"\",");
			sb.append("\"date\":\""+generalDocument.getDATE()+"\",");
			sb.append("\"uploader\":\""+generalDocument.getUPLOADER()+"\",");
			sb.append("\"hit\":"+generalDocument.getHIT());
			if(i<generalDocumentList.size()-1) {
				sb.append("},");
			}else {
				sb.append("}");
			}
		}
		sb.append("]");
		sb.append("}");
		System.out.println(sb.toString());
		
		return sb.toString();
	}
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File 등록 (Upload)!!
	// * 저장되는 File 이름은 Upload Page 에서 사용자가 작성한 File 명으로 저장한다.		--> File 명이 겹쳐 덮어쓰기 될경우를 방지
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public ModelAndView uploadGeneralDocument(String FileName, String Uploader, String description, MultipartFile uploadFile) {
//		System.out.println("\nGeneralDocumentController > uploadGeneralDocument > 넘어온 정보");
//		System.out.println("====================================");
//		System.out.println("FileName : "+FileName);
//		System.out.println("Uploader : "+Uploader);
//		System.out.println("Description : "+description);
//		System.out.println("uploadFile : "+uploadFile);
//		System.out.println("====================================");
		
		String result = null;
		
		//******************************************************************
		// 파일 확장자 구하기
		//******************************************************************
		String fileName = uploadFile.getOriginalFilename();	
		String ext = fileName.substring(fileName.lastIndexOf('.'));
		System.out.println("uploadFileName : "+fileName);
		
		
		//**************************************************************
		// 등록 File Data 가져오기 및 저장경로 지정, 저장
		//**************************************************************
		try {
			byte[] uploadFileBytes = uploadFile.getBytes();
			String savePath = "/home/gbridge/GeneralDocument/";
			File saveDir = new File(savePath);
			if(!saveDir.exists()) {
				saveDir.mkdirs();
			}
			Path path = Paths.get(savePath, FileName+ext);
			Files.write(path, uploadFileBytes);
			
			
			//**************************************************************
			// DB 에 Upload 이력 저장!
			//**************************************************************
			GeneralDocument dto = new GeneralDocument();
			dto.setFILE_NAME(FileName);
			dto.setPATH(savePath+FileName+ext);
			dto.setUPLOADER(Uploader);
			dto.setDSCPT(description);
			int insertResult = generalDocumentService.insertGeneralDocument(dto);		// DB 에 Data 저장!
			if(insertResult<=0) {														// 실패시
				File file = new File(savePath+FileName+ext);							// 저장된 File 삭제를 위해 File Instance 생성
				file.delete();															// File 삭제!
				result = "error/failUpload.html";										// ErrorPage 로 이동
			}else {			
				result = "board/generalDocumentBoard.html";							// 모두 성공시 일반 문서 자료실 목록으로!
			}
						
		}catch (Exception e) {
			e.printStackTrace();
			result = "/error/failUpload.html";
		}
		
		ModelAndView model = new ModelAndView();
		model.addObject("result", result);
		model.setViewName(result);
	
		return model;
	}
	
	
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File 삭제!!
	// * Transaction 상황을 고려하면 Service 객체 내부에서 진행해야 하지만 사용자와의 커뮤니케이션을 고려하여 Controller 에서 지정한 방식으로 결과값을 반환한다.
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	@ResponseBody
	public String deleteGeneralDocument(String fileName, int seq) {		
		JSONObject json = new JSONObject();
		GeneralDocument file = generalDocumentService.selectGeneralDocument(fileName, seq);
		if(file == null) {
			json.put("resultCode", -1);
			System.out.println("DB 에 파일 정보가 존재하지 않음!");			
			//return json.toString();
		}
		System.out.println("파일 존재함!");
		String deletePath = file.getPATH();
		System.out.println("삭제할 파일 Path : "+deletePath);
		File deleteFile = new File(deletePath);
		if(!deleteFile.delete()) {
			json.put("resultCode", -2);
			System.out.println("서버 디스크에 파일이 존재하지 않음!");			
			//return json.toString();
		}
		System.out.println("서버 디스크 파일 삭제 완료!");
		int deleteResultFromDB = generalDocumentService.deleteGeneralDocument(fileName, seq);
		json.put("resultCode", deleteResultFromDB);	
		System.out.println("서버 DB Data 삭제 완료!");		
		
		return json.toString();
	}
	
	
	
	
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File 상세보기 이동
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/detailPage", method=RequestMethod.GET)
	public ModelAndView goDetailGeneralDocument(String fileName, int seq) {
		System.out.println("GeneralDocumentController > goDetailGeneralDocument > Parameter > fileName : "+fileName+" / seq : "+seq);
		GeneralDocument file = generalDocumentService.selectGeneralDocument(fileName, seq);
		ModelAndView model = new ModelAndView();
		model.addObject("fileName", file.getFILE_NAME());
		model.addObject("seq", file.getSEQ());
		model.setViewName("board/generalDocumentDetail.html");
		return model;
	}
	
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File 하나의 상세내용 가져오기
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	@ResponseBody
	public String getDetailGeneralDocument(String fileName, int seq) {
		GeneralDocument generalDocument = generalDocumentService.selectGeneralDocument(fileName, seq);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"seq\":"+generalDocument.getSEQ()+",");
		sb.append("\"fileName\":\""+generalDocument.getFILE_NAME()+"\",");
		sb.append("\"path\":\""+generalDocument.getPATH()+"\",");
		sb.append("\"uploader\":\""+generalDocument.getUPLOADER()+"\",");
		sb.append("\"date\":\""+generalDocument.getDATE()+"\",");
		sb.append("\"hit\":"+generalDocument.getHIT()+",");
		sb.append("\"dscpt\":\""+generalDocument.getDSCPT()+"\"");
		sb.append("}");
	
		return sb.toString();
	}
	
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File 내용 수정
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public ModelAndView editGeneralDocument(int seq, String FileName, String path, String Uploader, String description, MultipartFile uploadFile) {
		String modifyFileName = uploadFile.getOriginalFilename();	// 확장자 포함한 이름
		System.out.println("넘어온 Path : "+path);
		String nextPage = null;
		int updateResult = 0;
		
		//*********************************************************************************************************
		// File 정보도 수정되는지를 Check
		// 만약 수정 페이지에서 File 을 선택해서 Parameter 를 넘겼다면 getOriginalFilename() Method 를 통해 구하는 값이 존재할 것이다.
		//*********************************************************************************************************
		if(modifyFileName.equals("")) {																				// 파일 이름이 없다면 파일 수정 안하는것			
			updateResult = generalDocumentService.updateGeneralDocument(FileName, seq, description);				// 게시물 Data 만 수정한다		
		}else {
			System.out.println("\nGeneralDocumentController > editGeneralDocument > 파일이 수정됩니다!!");
			//************************************************
			// 파일명 지정! (확장자 구하기)
			//************************************************
			String exrt = modifyFileName.substring(modifyFileName.lastIndexOf('.'));
			String newFileName = FileName+exrt;									
						
			
			//************************************************
			// File 저장하기
			//************************************************
			try {
				byte[] newFileBytes = uploadFile.getBytes();									
				String savePath = "/home/gbridge/GeneralDocument/";
				File saveDir = new File(savePath);
				if(!saveDir.exists()) {
					saveDir.mkdirs();
				}
				Path newPath = Paths.get(savePath, newFileName);
				Files.write(newPath, newFileBytes);
				
				//************************************************
				// DB 내용 수정하기
				//************************************************
				GeneralDocument dto = new GeneralDocument();
				dto.setSEQ(seq);
				dto.setFILE_NAME(FileName);
				dto.setPATH(savePath+FileName+exrt);
				dto.setUPLOADER(Uploader);
				dto.setDSCPT(description);
				updateResult = generalDocumentService.updateGeneralDocument(dto);
				if(updateResult > 0) {								// 수정이 모두 끝났으면
					File prevFile = new File(path);					// 이전에 등록되어있던 File 찾아서
					prevFile.delete();								// 삭제
				}else {												// 수정 실패하면
					File newFile = new File(newPath+newFileName);	// 새로 저장한 File 을 찾아서
					newFile.delete();								// 삭제
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		//**********************************************************************
		// 작업 이후 이동할 Page 지정
		//**********************************************************************
		if(updateResult <= 0) {
			nextPage = "error/failEdit.html";
		}else {
			//nextPage = "/board/generalDocumentDetail.html?fileName="+FileName+"&seq="+seq;\
			nextPage = "board/generalDocumentDetail.html";		
		}
		
		//************************************************************************
		// Thymelear Resolver 의 관여로 URL 에 get 방식 처럼 Parameter 를 줄 수 없다
		// 따라서 ModelAndView 를 이용하여 Forwarding 한다.
		//************************************************************************
		ModelAndView model = new ModelAndView();
		model.addObject("updateResult", updateResult);
		model.addObject("fileName", FileName);
		model.addObject("seq", seq);
		model.setViewName(nextPage);
		
		return model;
	}
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// File Download!!
	// File Download 진행!!
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile(String fileName, String seq_file, HttpServletRequest request) {			
		ResponseEntity<Resource> result = null;
		
		int seq = Integer.parseInt(seq_file);
		GeneralDocument file = generalDocumentService.selectGeneralDocument(fileName, seq);
		int updateHitResult = generalDocumentService.updateHit(fileName, seq);
		if(updateHitResult == 0) {
			return result;
		}else {
			
			//headers.setContentType(MediaType.valueOf("text/plain;charset=UTF-8"));
			
			//******************************************************************************************************
			// File 보내기!
			//******************************************************************************************************
			Resource resource = generalDocumentService.loadGeneralDocumentAsResource(fileName, seq);
			
			
			//***********************************
			// 전송할 File 의 MIME Type 과 파일명을 구함
			//***********************************
			String contentType = null;
			String file_name = null;
	        try {	        		        		 
	        	//**********************************************************************
	        	// File 전송시 File 의 TYPE 에 해당하는 MIME Type 을 구함
	        	//**********************************************************************
	            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());		           
	            //file_name = URLEncoder.encode(resource.getFilename(), "UTF-8");
	            file_name = resource.getFilename();
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
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file_name + "\"")
	                .body(resource);	 
	        
	        return result; 
		}
	}
}
