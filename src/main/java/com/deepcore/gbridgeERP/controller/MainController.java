package com.deepcore.gbridgeERP.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deepcore.gbridgeERP.model.service.FileService;

@Controller
public class MainController {
	
	@Autowired
	@Qualifier("fileServiceImpl")
	FileService fileService;	
	
	
	//--------------------------------------------------------------------------------------------
	// 로그인 승인시 Main Page 로 이동
	//--------------------------------------------------------------------------------------------
	@RequestMapping("/admin/main")
	public String goMain(HttpSession session) {
		System.out.println("MainController > goMain > 요청 수신!");
	
		return "main.html";
	}		
}
	