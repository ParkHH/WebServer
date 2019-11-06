package com.deepcore.gbridgeERP.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepcore.gbridgeERP.common.Sha256;
import com.deepcore.gbridgeERP.model.entity.Account;
import com.deepcore.gbridgeERP.model.service.AccountService;

/**
 * 
 * @author ParkHyeonho
 * 접속 계정에 대한 요청을 처리를 진행하는 Controller Class
 */


@Controller
@RequestMapping("/admin/account")
public class AccountController {
	
	@Autowired
	@Qualifier("accountServiceImpl")
	AccountService accountService;
	
	//-------------------------------------------------------------------------
	// 비밀번호 변경
	//-------------------------------------------------------------------------
	@RequestMapping(value="/newPwd", method=RequestMethod.POST)
	@ResponseBody
	public String createNewPassword(HttpServletRequest request, String prevPass, String newPass, String newPassConfirm) {
		HttpSession session = request.getSession();
		String ACNT_ID = session.getAttribute("ACNT_ID").toString();
		String ACNT_PW = session.getAttribute("ACNT_PW").toString();
		
		System.out.println("\nAccountController > createNewPassword > Parameter > 1.prevPass : "+prevPass+" / 2. newPass : "+newPass+" / 3.newPassConfirm : "+newPassConfirm);
		System.out.println("\nAccountController > createNewPassword > ACNT_ID : "+ACNT_ID+" / ACNT_PW : "+ACNT_PW);
		
		JSONObject json = new JSONObject();
		//**************************************************
		// 입력한 이전 비밀번호가 DB 에 등록된 이전 비밀번호와 같은지 비교
		// 1) 입련된 비밀번호 Hash 처리 진행 후 비교
		// 2) 같으면 입력한 새비밀번호 값 비교
		//**************************************************
		
		String shaPrevPass = "*"+Sha256.applySha256(prevPass).toUpperCase();
		
		if(shaPrevPass.equals(ACNT_PW)) {
			if(newPass.equals(newPassConfirm)) {
				String shaNewPass = Sha256.applySha256(newPass);
				Account account = new Account();
				account.setACNT_PW(("*"+shaNewPass).toUpperCase());
				account.setACNT_ID(ACNT_ID);
				int updatePasswordResult = accountService.updateAccount(account);
				if(updatePasswordResult>0) {
					json.put("resultCode", "1");
				}else {
					json.put("resultCode", "0");
				}
			}else {
				json.put("resultCode", "-1");
			}
		}else {
			json.put("resultCode", "-2");
		}
		
		return json.toString();
	}
}
