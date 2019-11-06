package com.deepcore.gbridgeERP.model.serviceImpl;

import java.util.List;

import com.deepcore.gbridgeERP.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepcore.gbridgeERP.model.dto.AccountInfo;
import com.deepcore.gbridgeERP.model.entity.Account;
import com.deepcore.gbridgeERP.model.service.AccountService;
import com.deepcore.gbridgeERP.repository.AccountRepository;
import com.deepcore.gbridgeERP.repository.EmployRepository;


/**
 * 
 * @author ParkHyeonho
 * 계정정보에 관련한 CRUD 작업을 수행을 Repository 에게 지시
 */

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	EmployRepository employRepository;

	@Autowired
	CompanyRepository companyRepository;

	//----------------------------------------------------------------
	// 모든 계정정보 조회
	//----------------------------------------------------------------
	@Override
	public List<Account> selectAllAccount() {
		List<Account> accountList = accountRepository.findAll();
		return accountList;
	}

	
	//----------------------------------------------------------------
	// Parameter id, pw 정보를 모두 갖는 계정정보 조회 (로그인)
	//----------------------------------------------------------------
	@Override
	public AccountInfo selectAccount(Account account) {
		String ACNT_ID = account.getACNT_ID();
		String ACNT_PW = account.getACNT_PW();

		Account getAccount = accountRepository.findAccountInfo(ACNT_ID, ACNT_PW);

		AccountInfo accountInfo = new AccountInfo();

		if (getAccount != null) {
			accountInfo.setTABL_CNMB(getAccount.getTABL_CNMB());
			accountInfo.setCMPN_NO(getAccount.getCMPN_NO());
			accountInfo.setCMPN_NM(getAccount.getCMPN_NM());
			accountInfo.setEMPL_NO(getAccount.getEMPL_NO());
			accountInfo.setEMPL_NM(getAccount.getEMPL_NM());
			accountInfo.setDEPT_NM(getAccount.getDEPT_NM());
			accountInfo.setPSTN_NM(getAccount.getPSTN_NM());
			accountInfo.setPSTN_CD(getAccount.getPSTN_CD());
			accountInfo.setACNT_ID(getAccount.getACNT_ID());
			accountInfo.setACNT_PW(getAccount.getACNT_PW());
			accountInfo.setMAC(getAccount.getMAC());
			accountInfo.setCNNC_PRMI_YN(getAccount.getCNNC_PRMI_YN());
			accountInfo.setHLOF_DVSN_CD(getAccount.getHLOF_DVSN_CD());
			accountInfo.setSCRN_SORT_ORD(getAccount.getSCRN_SORT_ORD());
		}

		return accountInfo;
	}

	@Override
	public int insertAccount(Account account) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAccount(Account account) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	//------------------------------------------------------------------------
	// 사용자 계정정보 수정!
	//------------------------------------------------------------------------
	@Override
	public int updateAccount(Account account) {
		String newACNT_PW = account.getACNT_PW();
		String ACNT_ID = account.getACNT_ID();
		
		int changePasswordResult = accountRepository.changePassword(newACNT_PW, ACNT_ID);
		
		return changePasswordResult;
	}

}
