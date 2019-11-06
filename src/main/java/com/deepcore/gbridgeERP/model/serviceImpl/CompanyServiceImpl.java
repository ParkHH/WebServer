package com.deepcore.gbridgeERP.model.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.deepcore.gbridgeERP.model.entity.Company;
import com.deepcore.gbridgeERP.model.service.CompanyService;
import com.deepcore.gbridgeERP.repository.CompanyRepository;

/**
 * 
 * @author ParkHyeonho
 * Controller 로 부터 지시받은 요청관련 CRUD 작업을 수행하는 ServiceClass
 */
@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	@Qualifier("companyRepository")
	CompanyRepository companyRepository;
	
	@Override
	public List<Company> selectAllCompany() {
		List<Company> companyList = companyRepository.findAll();
		return companyList;
	}

	@Override
	public Company selectCompany(Company company) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertCompany(Company company) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteCompany(Company company) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateCompany(Company company) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
