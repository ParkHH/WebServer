package com.deepcore.gbridgeERP.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * @author ParkHyeonho
 * 직원 정보 Data 를 담는 DB 의 TB_EMPL_M 과 대응되는 Entity 객체
 */

@Data
@Entity
@Table(name="TB_EMPL_M")
public class Employ {
	@Id
	@Column(name="EMPL_NO")
	String EMPL_NO;
	
	@Column(name="EMPL_NM")
	String EMPL_NM; 
	
	@Column(name="PSTN_CD")
	String PSTN_CD;
	
	@Column(name="DEPT_NM")
	String DEPT_NM;
	
	@Column(name="ENCP_DT")
	String ENCP_DT; 
	
	@Column(name="LVCP_DT")
	String LVCP_DT;
	
	@Column(name="HLOF_DVSN_CD")
	String HLOF_DVSN_CD; 
	
	@Column(name="CELL_PHNE_NO")
	String CELL_PHNE_NO;
	
	@Column(name="EMAL_ADDR")
	String EMAL_ADDR;
	
	@Column(name="HOME_ADDR")
	String HOME_ADDR;
	
	@Column(name="EMPL_MEMO_CNTS")
	String EMPL_MEMO_CNTS;	
}
