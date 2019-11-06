package com.deepcore.gbridgeERP.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.deepcore.gbridgeERP.exception.FileDownloadException;
import com.google.common.net.HttpHeaders;

/**
 * 
 * @author ParkHyeonho
 * Session Check 등 공통 부분 요청 처리 Controller
 */

@Controller
@RequestMapping("/admin/common")
public class CommonController {
	
	
	//-----------------------------------------------------------------------------------------------------------------
	// 접속 아이디 가져오기!
	//-----------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/session", method=RequestMethod.GET)
	@ResponseBody
	public String getSession(HttpSession session) {
		//HttpSession session = request.getSession();
		if(session.getAttribute("ACNT_ID") == null) {
			System.out.println("session 이 null 값입니다.");
			return "/admin";
		}
		String ACNT_ID = session.getAttribute("ACNT_ID").toString();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ACNT_ID", ACNT_ID);
		
		return jsonObject.toString();
	}
	
	
	
	
	//------------------------------------------------------------------------------------------------------------------
	// 설치파일 이름, 생성 일자 가져오기
	//------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/installFileName", method=RequestMethod.GET)
	@ResponseBody
	public String getInstallFileName() {
		//System.out.println("CommonController > getInstallFileName > 요청 수신!");
		String resultFileName = null;
		//String filePath = "C:/Users/ParkHyeonho/Desktop/work/IDE/workspace/visualStudio_workspace/SetupTripERP/Release/SetupTripERP.msi";				// 파일 Path
		String filePath = "/home/gbridge/setup/SetupTripERP.msi";				// 파일 Path
		File file = new File(filePath);											// File Instance 생성 (by filePath)
		Path pathFile = file.toPath();											// Path Instance 생성 (by file)
		Resource resource = null;												
		JSONObject json = null;
		try {
			resource = new UrlResource(pathFile.toUri());						// resource Instance 생성 (by filePath)
			if(resource.exists()) {												// resource Instance 가 생성되었다면
				resultFileName = resource.getFilename();						// resource Instance 의 file 의 file 이름을 가져옴
				long lastModified = file.lastModified();						// file 의 최종 수정일 정보를 가져옴
				String pattern = "yyyy-MM-dd hh:mm aa";							// file 의 최종 수정일의 String format 을 지정
				SimpleDateFormat format = new SimpleDateFormat(pattern);		// 생성한 format 으로 날짜 format Instance 를 생성
				Date lastModifiedDate = new Date(lastModified);					// file 최종 수정일을 long 에서 Date 으로 형변환
				String lastModifiedDateStr = format.format(lastModifiedDate);	// 생성한 format Instance 로 Date 로 형변환한 최종수정일을 String 형식으로 변환
				
				json = new JSONObject();										// 응답을 위해 JSONObject 사용
				json.put("fileName", resultFileName);							// fileName 의 key 값으로 resource Instance 에서 구한 fileName 을 담음	
				json.put("modifiedDate", lastModifiedDateStr);					// modifiedDate 의 key 값으로 String 형식으로 최종 형변환된 file 최종수정일 정보를 담음								
			}else {
				throw new FileNotFoundException("파일이 존재하지 않습니다.");			// filePath 에 해당하는 file 이 존재하지 않는다면 Exception 발생
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		
		return json.toString();			// Exception 발생없이 정상동작되었을 경우 Client 에 JSON 정보를 String 화하여 반환
	}
	
	
	
	//------------------------------------------------------------------------------------------------------------------
	// ERP 설치 파일 Upload
	//------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/installFileRegistration", method=RequestMethod.POST)
	public ModelAndView uploadInstallFile(@RequestParam("uploadFileParam") MultipartFile fileData) {
		System.out.println("\nCommonController > uploadInstallFile > fileData : fileData");
		ResponseEntity<String> result = null;
		ModelAndView model = new ModelAndView();
		model.setViewName("fragments/layout.html");
		try {
			byte[] fileByte = fileData.getBytes();
			//String savePath = "C:/Users/ParkHyeonho/Desktop/work/IDE/workspace/visualStudio_workspace/SetupTripERP/";
			String savePath = "/home/gbridge/setup/";
			String saveFileName = fileData.getOriginalFilename();
			File saveFile = new File(savePath);			
			if(!saveFile.exists()) {
				saveFile.mkdirs();
			}
			Path savingPath = Paths.get(savePath, saveFileName);
			Files.write(savingPath, fileByte);			
			result = new ResponseEntity<String>(HttpStatus.OK);			
			model.addObject("uploadState", result);
		} catch (IOException e) {
			e.printStackTrace();
			result = new ResponseEntity<String>(HttpStatus.FAILED_DEPENDENCY);
			model.addObject("uploadState", result);
		}
		
		
		return model;
	}
	
	
	//------------------------------------------------------------------------------------------------------------------
	// ERP 설치 파일 Download
	//------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/DownloadInstallFile", method=RequestMethod.GET)
	public ResponseEntity<Resource> downloadInstallFile(HttpServletRequest request){
		//System.out.println("CommonController > downloadInstallFile > 요청 수신!");
		String filePath = "/home/gbridge/setup/SetupTripERP.msi";
		ResponseEntity<Resource> resourceEntity = null;
		Resource resource = null;
		File file = new File(filePath);
		Path path = file.toPath();
		ResponseEntity<Resource> result = null;		
		try {
			resource = new UrlResource(path.toUri());
			if(resource.exists()) {
				String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
				String endcodedFileName = URLEncoder.encode(resource.getFilename(), "UTF-8");
				if(contentType == null){
					contentType="application/octet-stream";
				}
				result = ResponseEntity.ok()
						.contentType(MediaType.parseMediaType(contentType))
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+endcodedFileName+"\"")
						.body(resource);
			}else {
				throw new FileDownloadException("파일을 찾을 수 없습니다.");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new FileDownloadException("파일을 찾을 수 없습니다.");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
}
