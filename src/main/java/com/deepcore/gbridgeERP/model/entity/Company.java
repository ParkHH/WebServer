package com.deepcore.gbridgeERP.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


/**
 * 
 * @author ParkHyeonho
 * Table TB_COOP_CMPN_M 과 대응되어 CRUD 에 필요한 Data 를 담을 Entity Class
 */

@Entity
@Table(name="TB_COOP_CMPN_M")
@Data
public class Company {
	
	@Id
	@Column(name="CMPN_NO")
	String CMPN_NO;
	
	@Column(name="CMPN_NM")
	String CMPN_NM;
}
