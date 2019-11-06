package com.deepcore.gbridgeERP.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * @author ParkHyeonho
 * 일반 문서 자료실에서 DB 내 TB_GNRL_DOCU_FILE Table 과 대응되는 Entity 객체
 */


@Data
@Entity
@Table(name="TB_GNRL_DOCU_FILE")
public class GeneralDocument {
	@Id
	@Column(name="SEQ")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int SEQ;
	
	@Column(name="FILE_NAME", columnDefinition="VARCHAR(20) NOT NULL")
	String FILE_NAME;
	
	@Column(name="PATH", columnDefinition="VARCHAR(500) NOT NULL")
	String PATH;
	
	@Column(name="UPLOADER", columnDefinition="VARCHAR(10) NOT NULL")
	String UPLOADER;
	
	@Column(name="DATE", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	String DATE;
	
	@Column(name="HIT", columnDefinition="INT(100) DEFAULT 0")
	int HIT;
	
	@Column(name="DSCPT", columnDefinition="VARCHAR(1000) DEFAULT NULL")
	String DSCPT;
}
