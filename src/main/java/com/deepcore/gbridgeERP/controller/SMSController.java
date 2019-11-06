package com.deepcore.gbridgeERP.controller;

import com.deepcore.gbridgeERP.model.dto.CurrentAccountDepositSearchListDto;
import com.deepcore.gbridgeERP.model.entity.CurrentAccount;
import com.deepcore.gbridgeERP.model.entity.CurrentAccountDeposit;
import com.deepcore.gbridgeERP.model.service.CurrentAccountDepositService;
import com.deepcore.gbridgeERP.repository.CurrentAccountDepositRepository;
import com.deepcore.gbridgeERP.repository.CurrentAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
//@RequestMapping(value = "/sms", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/sms", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class SMSController {

    private CurrentAccountDepositService currentAccountDepositService;

    @Autowired
    private CurrentAccountRepository currentAccountRepository;

    @Autowired
    public SMSController(CurrentAccountDepositService currentAccountDepositService) {
        this.currentAccountDepositService = currentAccountDepositService;
    }

    @PostMapping
    public CurrentAccountDeposit InsertMessage(@RequestParam Map<String, Object> in_message) throws org.json.simple.parser.ParseException {
        CurrentAccountDepositSearchListDto listDto = new CurrentAccountDepositSearchListDto();

        listDto.setCurrentAccountNoList(currentAccountRepository.findAllBy());

        String _date = "";
        String _message = "";
        String _message_acct_no = "";
        String _message_acct_no_substr = "";
        String _message_transaction_date = "";
        String _message_customer_name = "";
        String _message_transaction_money = "";
        String _message_remk = "";

        boolean is_exits_acct_no = false;

        for (Map.Entry<String, Object> entry : in_message.entrySet()) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(entry.getKey());

            _date = jsonObject.get("date").toString();
            _message = jsonObject.get("message").toString();

            System.out.println(_date);
            System.out.println(_message);

            String[] split_message = _message.split("\\n");
            for(int i=0; i < split_message.length; i++) {
                if (i == 1)
                    _message_transaction_date = split_message[i].substring(4);
                if (i == 2) {
                    _message_acct_no_substr = split_message[i];
                    _message_acct_no = split_message[i].substring(0, 6) + split_message[i].substring(8, 11);
                }
                if (i == 3)
                    _message_customer_name = split_message[i];
                if (i == 4)
                    _message_remk = split_message[i];
                if (i == split_message.length - 1)
                    _message_transaction_money = split_message[i];
            }
        }
        String NONE_ASTRK_ACCT_NO = "";
        for (CurrentAccount item: listDto.getCurrentAccountNoList()) {
            String IN_ACCT_NO = item.getAccountNo().replaceAll("-", "");
            if (IN_ACCT_NO.length() > 10) {
                NONE_ASTRK_ACCT_NO = IN_ACCT_NO.substring(0, 6) + IN_ACCT_NO.substring(8, 11);
                if (NONE_ASTRK_ACCT_NO.equals(_message_acct_no)) {
                    NONE_ASTRK_ACCT_NO = item.getAccountNo();
                    is_exits_acct_no = true;
                    break;
                }
            }
        }

        String setAccountNo = "";
        if (is_exits_acct_no)
            setAccountNo = NONE_ASTRK_ACCT_NO;
        else
            setAccountNo = _message_acct_no;

        CurrentAccountDeposit message = new CurrentAccountDeposit();

        message.setBankCode("004");                 // 입금계좌 : 국민은행
        message.setAccountNo(setAccountNo);         // 입금 492, 출금 658
        message.setCurrencyCode("KRW");             // 통화코드
        message.setTransactionExchangeRate("1.0");  // KRW : 1.0 거래환율
        message.setTransactionDate(new Date());     // 거래일자
        message.setDepositWithDrawWonAmount(Double.parseDouble((_message_transaction_money).replaceAll("\\,", "")));
        message.setDepositWithDrawForeignCurrencyAmount(0.0);
        message.setDepositWithDrawWonBalance(Double.parseDouble((_message_transaction_money).replaceAll("\\,", "")));
        message.setDepositWithDrawForeignCurrencyBalance(0.0);
        message.setTransactionCustomerName(_message_customer_name);
        message.setDepositWithDrawDivisonCode("1");  // 입출금 구분 1: 입금
        message.setTransactionAccountTitCode("100"); // 당좌자산 코드 100
        message.setTransactionCompanyNo("0");        // 협력업체번호 :
        message.setRemarkContents(_message_remk);    // 비고내용
        message.setFirstRegisterId("SYSTEM");        // 등록자ID

        currentAccountDepositService.updateCurrentAccount(setAccountNo, Double.parseDouble((_message_transaction_money).replaceAll("\\,", "")), 0.0);

        return currentAccountDepositService.join(message);
    }
}
