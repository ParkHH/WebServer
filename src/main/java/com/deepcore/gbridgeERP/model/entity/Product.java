package com.deepcore.gbridgeERP.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="TB_PRDT_M")
@Data
public class Product {
	@Id
	@Column(name="PRDT_CNMB")
	int PRDT_CNMB;
	
	@Column(name="PRDT_NM")
	String PRDT_NM;
}
