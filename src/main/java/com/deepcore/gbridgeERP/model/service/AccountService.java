package com.deepcore.gbridgeERP.model.service;

import java.util.List;

import com.deepcore.gbridgeERP.model.dto.AccountInfo;
import com.deepcore.gbridgeERP.model.entity.Account;

/**
 * 
 * @author ParkHyeonho
 * 로그인시 계정정보에 관련한 CRUD 작업을 수행
 */
public interface AccountService {
	public List<Account> selectAllAccount();
	public AccountInfo selectAccount(Account account);
	public int insertAccount(Account account);
	public int deleteAccount(Account account);
	public int updateAccount(Account account);
}
