package com.deepcore.gbridgeERP.model.dto;

import com.deepcore.gbridgeERP.model.entity.CurrentAccount;
import lombok.Data;

import java.util.List;

@Data
public class CurrentAccountDepositSearchListDto extends ListDto {
    public List<CurrentAccount> CurrentAccountNoList;
}
