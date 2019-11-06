package com.deepcore.gbridgeERP.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author ParkHyeonho
 * Menu (Top, Side) 항목에서 들어오는 요청을 받아 처리를 지시하는 Controller
 */

@Controller
@RequestMapping("/admin/menu")
public class MenuController {
	
	
	//-------------------------------------------------------------------------------------------
	// 사이드바 메뉴에서 자료실 이동! (문서 자료실 일반 문서 관리)
	//-------------------------------------------------------------------------------------------
	@RequestMapping(value="/generalDocumentBoard", method=RequestMethod.GET)
	public String generalDocumentBoard() {
		return "board/generalDocumentBoard.html";
	}
	
	
	
	
	//-------------------------------------------------------------------------------------------
	// 사이드바 메뉴에서 자료실 이동! (문서 자료실 Template 관리)
	//-------------------------------------------------------------------------------------------
	@RequestMapping(value="/templateBoard", method=RequestMethod.GET)
	public String templateBoard() {
		return "board/templateBoard.html";
	}
}
