package com.deepcore.gbridge.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deepcore.gbridgeERP.model.service.FileService;

@Controller
public class IndexController {
	//-------------------------------------------------------------------------------------------
	// 최초 접속시
	//-------------------------------------------------------------------------------------------
	@RequestMapping("/admin")
	public String goIndex() {
		System.out.println("MainController > goIndex > Login Page 요청!");
		
		return "loginForm.html";
	}	
}
	