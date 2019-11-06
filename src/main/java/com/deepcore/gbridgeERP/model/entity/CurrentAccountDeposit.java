package com.deepcore.gbridgeERP.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name="TB_CRAC_ACCT_DPWH_D")
public class CurrentAccountDeposit {
    @Id
    @Column(name = "DPWH_CNMB")
    private long ConsecutiveNumber;

    @Column(name = "BANK_CD")
    private String bankCode;

    @Column(name = "ACCT_NO")
    private String AccountNo;

    @Column(name = "CUR_CD")
    private String CurrencyCode;

    @Column(name = "TRAN_DT")
    private Date TransactionDate;

    @Column(name = "TRAN_EXRT")
    private String TransactionExchangeRate;

    @Column(name = "DPWH_DVSN_CD")
    private String DepositWithDrawDivisonCode;

    @Column(name = "DPWH_WON_AMT")
    private Double DepositWithDrawWonAmount;

    @Column(name = "DPWH_FRCR_AMT")
    private Double DepositWithDrawForeignCurrencyAmount;

    @Column(name = "DPWH_WON_BAL")
    private Double DepositWithDrawWonBalance;

    @Column(name = "DPWH_FRCR_BAL")
    private Double DepositWithDrawForeignCurrencyBalance;

    @Column(name = "TRAN_ACNT_TIT_CD")
    private String TransactionAccountTitCode;

    @Column(name = "TRAN_CMPN_NO")
    private String TransactionCompanyNo;

    @Column(name = "TRAN_CUST_NM")
    private String TransactionCustomerName;

    @Column(name = "REMK_CNTS")
    private String RemarkContents;

    @Column(name = "FRST_RGTR_ID", length = 40)		//최초등록자아이디
    @CreatedBy
    private String firstRegisterId = null;
}
