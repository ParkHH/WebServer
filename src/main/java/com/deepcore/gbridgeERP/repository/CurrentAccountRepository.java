package com.deepcore.gbridgeERP.repository;

import com.deepcore.gbridgeERP.model.entity.CurrentAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Integer> {
    List<CurrentAccount> findAllBy();

    CurrentAccount findByAccountNo(@Param("accountNo") String AccountNo);
}
