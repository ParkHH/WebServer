package com.deepcore.gbridgeERP.controller;

import java.util.Arrays;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepcore.gbridgeERP.model.service.OtpService;


/**
 * 
 * @author ParkHyeonho
 * OTP 요청을 위해 SerialKey 를 생성하고 요청 URL 을 만드는 기능과 OTP 입력시 유효한 값인지 Check 하는 기능을 보유하는 Controller Class
 */
@Controller
@RequestMapping("/admin/otp")
public class OtpController {
	
	@Autowired
	@Qualifier("otpServiceImpl")
	OtpService otpService;
	
	//----------------------------------------------------------------------------------------
	// 계정 연결 SerialKey 및 URL 생성
	//----------------------------------------------------------------------------------------
	@RequestMapping(value="/request", method=RequestMethod.GET)
	@ResponseBody
	public String createSerialKeyAndUrl(HttpServletRequest request) {
		String serialKeyAndUrl = otpService.createSerialKeyAndUrl(false);
		
		return serialKeyAndUrl;
	}
	
	
	
	
	
	//----------------------------------------------------------------------------------------
	// OTP 입력번호 유효성 CHECK
	//----------------------------------------------------------------------------------------
	public boolean check_code(String otpNum, String encodedKey) {
		boolean checkResult = otpService.check_code(otpNum, encodedKey);
		
		return checkResult;
	}

}
