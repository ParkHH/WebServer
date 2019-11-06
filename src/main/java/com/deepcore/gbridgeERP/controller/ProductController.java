package com.deepcore.gbridgeERP.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepcore.gbridgeERP.model.entity.Product;
import com.deepcore.gbridgeERP.model.service.ProductService;

/**
 * 
 * @author ParkHyeonho
 * 상품관련 처리 요청을 받고 Service 객체에게 처리를 지시하는 Class
 */

@Controller
@RequestMapping(value="/admin/product")
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
	
		
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// 상품 종류 조회!!
	// 자료실 Page 의 select Option 채움
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RequestMapping("/all")
	@ResponseBody
	public String getAllProduct() {
		List<Product> productList = productService.selectAllProduct();
		System.out.println("ProductController > getProducts() > 등록된 상품 종류 개수 : "+productList.size());
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"productList\" : [");
		for(int i=0; i<productList.size(); i++) {
			Product product = productList.get(i);
			sb.append("{");
			sb.append("\"PRDT_CNMB\":\""+product.getPRDT_CNMB()+"\",");
			sb.append("\"PRDT_NM\":\""+product.getPRDT_NM()+"\"");
			if(i<productList.size()-1) {
				sb.append("},");
			}else {
				sb.append("}");
			}
		}
		sb.append("]");
		sb.append("}");
			
		return sb.toString();
	}
}
