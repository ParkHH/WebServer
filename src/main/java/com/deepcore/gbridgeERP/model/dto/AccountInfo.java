package com.deepcore.gbridgeERP.model.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author ParkHyeonho
 * 계정정보와 직원정보가 DB 에서 서로 다른 Table 로 분리되어있으므로 두가지 내용을 조인한 내용을 전달하기 위해 사용하는 Class (DTO)
 */

@Data
@Entity
public class AccountInfo {
	int TABL_CNMB;

	@Id
	String CMPN_NO;
	String CMPN_NM;
	String EMPL_NO;
	String ACNT_ID;
	String ACNT_PW;
	String MAC;
	String CNNC_PRMI_YN;
	String DEPT_NM;
	String PSTN_NM;
	String PSTN_CD;
	String HLOF_DVSN_CD;
	int SCRN_SORT_ORD;
	String EMPL_NM;
	boolean codeCheckResult;
}
