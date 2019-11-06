package com.deepcore.gbridgeERP.model.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepcore.gbridgeERP.model.entity.Company;


/**
 * 
 * @author ParkHyeonho
 * Table TB_COOP_CMPN_M 에 CRUD 작업을 지시하는 Service Interface
 */
public interface CompanyService {
	public List<Company> selectAllCompany();		// 모든 업체 정보 조회
	public Company selectCompany(Company company);	// 특정 업체 정보 조회
	public int insertCompany(Company company);		// 특정 업체 정보 등록
	public int deleteCompany(Company company);		// 특정 업체 정보 삭제
	public int updateCompany(Company company);		// 특정 업체 정보 수정
}
