package com.deepcore.gbridgeERP.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deepcore.gbridgeERP.model.entity.Product;

/**
 * 
 * @author ParkHyeonho
 * TB_PRDT_M 에 관련하여 CRUD 를 수행하는 Repository Interface
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	//-------------------------------------------------------------------------
	// TB_PRDT_M Table 에서의 모든 상품일련번호, 상품명 조회
	//-------------------------------------------------------------------------
	@Query(nativeQuery=true, value="SELECT PRDT_CNMB, PRDT_NM FROM TB_PRDT_M")
	public List<Product> findAll();
}
