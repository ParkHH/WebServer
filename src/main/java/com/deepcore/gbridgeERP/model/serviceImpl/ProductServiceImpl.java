package com.deepcore.gbridgeERP.model.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.deepcore.gbridgeERP.model.entity.Product;
import com.deepcore.gbridgeERP.model.service.ProductService;
import com.deepcore.gbridgeERP.repository.ProductRepository;

/**
 * 
 * @author ParkHyeonho
 * 상품 정보에 관련된 CRUD 작업을 지시하는 Service Class
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	@Qualifier("productRepository")
	ProductRepository productRepository;
	
	@Override
	public List<Product> selectAllProduct() {
		List<Product> productList = productRepository.findAll();
		return productList;
	}

	@Override
	public Product selectProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertProduct(Product product) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteProduct(Product product) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateProduct(Product product) {
		// TODO Auto-generated method stub
		return 0;
	}

}
