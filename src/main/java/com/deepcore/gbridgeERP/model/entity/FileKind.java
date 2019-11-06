package com.deepcore.gbridgeERP.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import javax.persistence.Table;

/**
 * 
 * @author ParkHyeonho
 * File 종류 조회 결과를 담을 DTO
 */

@Entity
@Data
@Table(name="TB_CMMN_CD_VLID_VAL_D")
public class FileKind {
	
	@Id
	@Column(name="CD_VLID_VAL")
	String CD_VLID_VAL;
	
	@Column(name="CD_VLID_VAL_DESC")
	String CD_VLID_VAL_DESC;
}
