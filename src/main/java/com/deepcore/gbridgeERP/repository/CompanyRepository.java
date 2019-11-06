package com.deepcore.gbridgeERP.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deepcore.gbridgeERP.model.entity.Company;


/**
 * 
 * @author ParkHyeonho
 * Table TB_COOP_CMPN_M 에 CRUD 를 수행하는 Repository Interface
 */
public interface CompanyRepository extends JpaRepository<Company, String> {
	@Query(nativeQuery=true, value="SELECT CMPN_NO, CMPN_NM FROM TB_COOP_CMPN_M")
	public List<Company> findAll();
}
