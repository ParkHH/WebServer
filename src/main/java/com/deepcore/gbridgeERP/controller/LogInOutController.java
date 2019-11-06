package com.deepcore.gbridgeERP.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.deepcore.gbridgeERP.model.entity.Employ;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepcore.gbridgeERP.common.Sha256;
import com.deepcore.gbridgeERP.model.dto.AccountInfo;
import com.deepcore.gbridgeERP.model.entity.Account;
import com.deepcore.gbridgeERP.model.service.AccountService;
import com.deepcore.gbridgeERP.model.service.OtpService;

/**
 * @author ParkHyeonho
 * 로그인과 관련된 요청처리를 진행하는 Controller Class
 */
@Controller
@RequestMapping("/admin")
public class LogInOutController {

	@Autowired
	@Qualifier("accountServiceImpl")
	AccountService accountService;

	@Autowired
	@Qualifier("otpServiceImpl")
        OtpService otpService;

	//-------------------------------------------------------------------------------------------
	//login 시도
	//-------------------------------------------------------------------------------------------
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public String login(String id, String pwd, String otpNum, String encodedKey, String otpFlag, String loginProgram, HttpServletRequest request) {
		System.out.println("MainController > login() > 넘어온 Parameter ( id:"+id+" / pwd:"+pwd+" / otpNum : "+otpNum+" / otpFlag : "+otpFlag+" / loginProgram : "+loginProgram+" / encodedKey : "+encodedKey+" )");
		/*
		 * 넘겨 받은 Parameter 로 DB 비교한다.
		 * 비밀번호는 DB 에 SHA256 이 적용되어 있으므로 Parameter 와의 비교를 위해선
		 * 비밀번호도 SHA256 을 적용해서 보내야한다.
		 */
		
		String sha256Password = "";
		
		 // 비밀번호 암호화!	
		if(loginProgram.equals("WEB")){
		  sha256Password = "*"+Sha256.applySha256(pwd).toUpperCase();
		}else if(loginProgram.equals("ERP")) {
		  sha256Password = pwd;
		}
		 

		 //System.out.println("암호화 최종 : "+sha256Password);
		
		// 요청 Parameter 생성
		Account account = new Account();
		account.setACNT_ID(id);
		account.setACNT_PW(sha256Password);

		AccountInfo getAccount = accountService.selectAccount(account);
		//System.out.println("가져온 ID : "+getAccount.getACNT_ID());
		//System.out.println("가져온 PW : "+getAccount.getACNT_PW());
		//System.out.println("가져온 MAC : "+getAccount.getMAC());
		JSONObject jsonObj = new JSONObject();
		boolean checkResult;
		HttpSession session = request.getSession();

		if (getAccount.getACNT_ID() != null) {			
			if(otpFlag.equals("true")) {				
				checkResult = otpService.check_code(otpNum, encodedKey);
				getAccount.setCodeCheckResult(checkResult);
				session.setAttribute("ACNT_ID", getAccount.getACNT_ID());
				session.setAttribute("ACNT_PW", getAccount.getACNT_PW());
				session.setAttribute("EMPL_NM", getAccount.getEMPL_NM());
				session.setAttribute("codeCheckResult", checkResult);
								
				//======================================================
				// 세션에 담겼는지 확인
				//======================================================
	//			System.out.println(session.getAttribute("ACNT_ID").toString());
	//			System.out.println(session.getAttribute("ACNT_PW").toString());
	//			System.out.println(session.getAttribute("EMPL_NM").toString());
	
				jsonObj.put("ACNT_ID", getAccount.getACNT_ID());
				jsonObj.put("CMPN_NO", getAccount.getCMPN_NO());
				jsonObj.put("CMPN_NM", getAccount.getCMPN_NM());
				jsonObj.put("EMPL_NO", getAccount.getEMPL_NO());
				jsonObj.put("EMPL_NM", getAccount.getEMPL_NM());
				jsonObj.put("DEPT_NM", getAccount.getDEPT_NM());
				jsonObj.put("PSTN_NM", getAccount.getPSTN_NM());
				jsonObj.put("ACNT_PW", getAccount.getACNT_PW());
				jsonObj.put("CNNC_PRMI_YN", getAccount.getCNNC_PRMI_YN());
				jsonObj.put("HLOF_DVSN_CD", getAccount.getHLOF_DVSN_CD());
				jsonObj.put("SCRN_SORT_ORD", getAccount.getSCRN_SORT_ORD());
				jsonObj.put("codeCheckResult", getAccount.isCodeCheckResult());
			}else {				
				session.setAttribute("ACNT_ID", getAccount.getACNT_ID());
				session.setAttribute("ACNT_PW", getAccount.getACNT_PW());
				session.setAttribute("EMPL_NM", getAccount.getEMPL_NM());
				
				jsonObj.put("ACNT_ID", getAccount.getACNT_ID());
				jsonObj.put("CMPN_NO", getAccount.getCMPN_NO());
				jsonObj.put("CMPN_NM", getAccount.getCMPN_NM());
				jsonObj.put("EMPL_NO", getAccount.getEMPL_NO());
				jsonObj.put("EMPL_NM", getAccount.getEMPL_NM());
				jsonObj.put("DEPT_NM", getAccount.getDEPT_NM());
				jsonObj.put("PSTN_NM", getAccount.getPSTN_NM());
				jsonObj.put("ACNT_PW", getAccount.getACNT_PW());
				jsonObj.put("MAC", getAccount.getMAC());
				jsonObj.put("CNNC_PRMI_YN", getAccount.getCNNC_PRMI_YN());
				jsonObj.put("HLOF_DVSN_CD", getAccount.getHLOF_DVSN_CD());
				jsonObj.put("SCRN_SORT_ORD", getAccount.getSCRN_SORT_ORD());
				
			}
		}

		return jsonObj.toString();
	}
	
	
	
	

	//-------------------------------------------------------------------------------------------
	//logOut 시도 후 성공하면 로그인 Page 로
	//-------------------------------------------------------------------------------------------
	@RequestMapping("/logout")
	public String logOut(HttpSession session) {
		session.invalidate();
		
		return "loginForm.html";
	}
}
