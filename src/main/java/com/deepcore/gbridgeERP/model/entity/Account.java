package com.deepcore.gbridgeERP.model.entity;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="TB_ACNT_INFO_M")
public class Account {
	@Id
	@Column(name="TABL_CNMB")
	int TABL_CNMB;
	
	@Column(name="CMPN_NO")
	String CMPN_NO;
	
	@Column(name="EMPL_NO")
	String EMPL_NO;
	
	@Column(name="ACNT_ID")
	String ACNT_ID;
	
	@Column(name="ACNT_PW")
	String ACNT_PW;
		
	@Column(name="MAC")
	String MAC;
		
	@Column(name="CNNC_PRMI_YN")
	String CNNC_PRMI_YN;
	
	@Column(name="SCRN_SORT_ORD")
	int SCRN_SORT_ORD;

	@Column(name="CMPN_NM")
	String CMPN_NM;

	@Column(name="EMPL_NM")
	String EMPL_NM;

	@Column(name="DEPT_NM")
	String DEPT_NM;

	@Column(name="PSTN_NM")
	String PSTN_NM;

	@Column(name="PSTN_CD")
	String PSTN_CD;

	@Column(name="HLOF_DVSN_CD")
	String HLOF_DVSN_CD;
}
