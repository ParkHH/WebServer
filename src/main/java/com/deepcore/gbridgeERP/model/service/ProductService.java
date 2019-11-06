package com.deepcore.gbridgeERP.model.service;

import java.util.List;

import com.deepcore.gbridgeERP.model.entity.Product;

/**
 * 
 * @author ParkHyeonho
 * 상품관련 요청 처리 작업을 수행하는 Service Interface
 */
public interface ProductService {
	public List<Product> selectAllProduct();		// 모든 상품 정보 조회
	public Product selectProduct(Product product);	// 특정 상품 정보 조회
	public int insertProduct(Product product);		// 특정 상품 정보 등록
	public int deleteProduct(Product product);		// 특정 상품 정보 삭제
	public int updateProduct(Product product);		// 특정 상품 정보 수정
}
