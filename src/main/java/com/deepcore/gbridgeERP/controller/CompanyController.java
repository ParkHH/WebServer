package com.deepcore.gbridgeERP.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepcore.gbridgeERP.model.entity.Company;
import com.deepcore.gbridgeERP.model.entity.FileKind;
import com.deepcore.gbridgeERP.model.service.CompanyService;

/**
 * 
 * @author ParkHyeonho
 * 업체관련 요청을 받아 Service 에 처리를 지시하는 Controller Class
 * 또 Service 객체로 부터 요청 결과 Data 를 받아 가공후 반환함
 */

@Controller
@RequestMapping(value="/admin/company")
public class CompanyController {
	
	
	@Autowired
	@Qualifier("companyServiceImpl")
	CompanyService companyService;
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// 업체 종류 조회!!
	// 자료실 Page 의 selectBox Option 채움
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RequestMapping("/all")
	@ResponseBody
	public String getAllCompany() {
		List<Company> companyList = companyService.selectAllCompany();
		System.out.println("CompanyController > getAllCompany() > 등록된 업체 정보 개수 : "+companyList.size());
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"companyList\" : [");
		for(int i=0; i<companyList.size(); i++) {
			Company company = companyList.get(i);
			sb.append("{");
			sb.append("\"CMPN_NO\":\""+company.getCMPN_NO()+"\",");
			sb.append("\"CMPN_NM\":\""+company.getCMPN_NM()+"\"");
			if(i<companyList.size()-1) {
				sb.append("},");
			}else {
				sb.append("}");
			}
		}
		sb.append("]");
		sb.append("}");
			
		return sb.toString();
	}
				
}
