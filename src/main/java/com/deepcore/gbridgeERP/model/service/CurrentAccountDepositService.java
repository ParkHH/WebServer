package com.deepcore.gbridgeERP.model.service;

import com.deepcore.gbridgeERP.model.entity.CurrentAccount;
import com.deepcore.gbridgeERP.model.entity.CurrentAccountDeposit;

public interface CurrentAccountDepositService {
    CurrentAccountDeposit join(CurrentAccountDeposit currentAccountDeposit);

    CurrentAccount updateCurrentAccount(String AccountNo, Double WonBalance, Double ForeignBalance);
}
