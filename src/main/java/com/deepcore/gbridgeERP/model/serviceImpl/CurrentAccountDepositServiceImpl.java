package com.deepcore.gbridgeERP.model.serviceImpl;

import com.deepcore.gbridgeERP.model.entity.CurrentAccount;
import com.deepcore.gbridgeERP.model.entity.CurrentAccountDeposit;
import com.deepcore.gbridgeERP.model.service.CurrentAccountDepositService;
import com.deepcore.gbridgeERP.repository.CurrentAccountDepositRepository;
import com.deepcore.gbridgeERP.repository.CurrentAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrentAccountDepositServiceImpl implements CurrentAccountDepositService {

    @Autowired
    private CurrentAccountDepositRepository currentAccountDepositRepository;

    @Autowired
    private CurrentAccountRepository currentAccountRepository;

    @Override
    public CurrentAccountDeposit join(CurrentAccountDeposit currentAccountDeposit) {
        return currentAccountDepositRepository.save(currentAccountDeposit);
    }

    @Override
    public CurrentAccount updateCurrentAccount(String AccountNo, Double WonBalance, Double ForeignBalance) {
        CurrentAccount currentAccount = currentAccountRepository.findByAccountNo(AccountNo);
        currentAccount.setWonBalance(currentAccount.getWonBalance() + WonBalance);

        return currentAccountRepository.save(currentAccount);
    }
}
