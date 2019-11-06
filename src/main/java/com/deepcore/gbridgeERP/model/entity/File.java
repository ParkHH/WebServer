package com.deepcore.gbridgeERP.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;
import org.springframework.data.repository.query.parser.Part.Type;

import lombok.Data;

/**
 * 
 * @author ParkHyeonho
 *	
 * DB 의 File Table 과 대응될 Entity 객체
 * DTO 역할도 동시에 수행
 */

@Data
@Entity
@Table(name="tb_file")
public class File {
	@Id
	@Column(name="seq")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int seq_file;	
	
	@Column(name="file_kind",columnDefinition="varchar(4) NOT NULL")				// 문서의 종류
	private String fileKind;
	
	@Column(name="cmpn_no", columnDefinition="varchar(4) NOT NULL")					// 업체번호
	private String CMPN_NO;
	
	@Column(name="prdt_cnmb", columnDefinition="int(20) NOT NULL")					// 상품일련번호
	private int PRDT_CNMB;
	
	@Column(name="file_name", columnDefinition="varchar(100) default null")			// 업로드 파일 이름
	private String fileName;	
	
	@Column(name="path", columnDefinition="varchar(400) default null")				// 업로드된 경로
	private String path;
	
	@Column(name="uploader", columnDefinition="varchar(30) default null")			// 업로드한 인원 정보
	private String uploader;
	
	@Column(name="date", columnDefinition="timestamp default current_timestamp")	// 업로드한 날짜
	private String date;
	
	@Column(name="hit", columnDefinition="integer default 0")						// 다운로드 횟수
	private int hit;
}
