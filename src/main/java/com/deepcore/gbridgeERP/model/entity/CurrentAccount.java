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
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "insertCurrentAccountDeposit",
                procedureName = "InsertCurrentAccountDeposit",
                resultClasses = Account.class)
})

@IdClass(CurrentAccountId.class)
@Table(name="TB_CRAC_ACCT_M")
public class CurrentAccount {
    @Id
    @Column(name = "BANK_CD")
    private String bankCode;

    @Id
    @Column(name = "ACCT_NO")
    private String accountNo;

    @Column(name = "CUR_CD")
    private String currenyCode;

    @Column(name = "ACCT_OPEN_DT")
    private Date accountOpenDate;

    @Column(name = "BANK_BZBR_NM")
    private String bankBizBrName;

    @Column(name = "BANK_RPTV_NM")
    private String bankRPTVName;

    @Column(name = "ACCT_NM")
    private String accountName;

    @Column(name = "BANK_RPTV_PHNE_NO")
    private String bankRPTVPhoneNumber;

    @Column(name = "BANK_RPTV_EMAL_ADDR")
    private String bankRPTVEmailAddress;

    @Column(name = "WON_BAL")
    private Double wonBalance;

    @Column(name = "FRCR_BAL")
    private Double foreignCurrencyBal;

    @Column(name = "USE_YN")
    private String useYesNo;

    @Column(name = "FRST_RGTR_ID", length = 40)		//최초등록자아이디
    @CreatedBy
    private String firstRegisterId = null;
}
