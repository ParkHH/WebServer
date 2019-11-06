package com.deepcore.gbridgeERP.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deepcore.gbridgeERP.model.entity.Employ;

public interface EmployRepository extends JpaRepository<Employ, String> {
	
	//----------------------------------------------------------------
	// 전체조회
	//----------------------------------------------------------------
	@Query(nativeQuery=true, value="SELECT * FROM TB_EMPL_M")
	public List<Employ> findAll();
	
	
	//----------------------------------------------------------------
	// 특정 직원 정보 조회
	//----------------------------------------------------------------
	@Query(nativeQuery=true, value="SELECT * FROM TB_EMPL_M WHERE EMPL_NO=:EMPL_NO")
	public Employ findEmploy(@Param("EMPL_NO")String EMPL_NO);
	
}
