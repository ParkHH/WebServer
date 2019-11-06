package com.deepcore.gbridgeERP.repository;

import java.util.List;

import com.deepcore.gbridgeERP.model.dto.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.deepcore.gbridgeERP.model.entity.Account;

/**
 * 
 * @author ParkHyeonho
 * Table TB_ACNT_INFO_M 에 대해 CRUD 를 수행하는 Class 
 */
public interface 	AccountRepository extends JpaRepository<Account, Integer> {
	//---------------------------------------------------------
	// 모든 계정 정보 가져오기
	//---------------------------------------------------------
	@Query(nativeQuery=true, value="SELECT * FROM TB_ACNT_INFO_M")
	public List<Account> findAll();
	
	
	//-------------------------------------------------------------------------------------------------------------------
	// 아이디와 비밀번호 값이 일치하는 계정정보 가져오기 (로그인) --> TripERP, 관리자웹페이지 별개로 진행됬을때 썼던 쿼리
	//-------------------------------------------------------------------------------------------------------------------
	@Query(nativeQuery=true, value="SELECT * FROM TB_ACNT_INFO_M A, TB_CMMN_CD_VLID_VAL_D B, TB_CMMN_CD_VLID_VAL_D C WHERE A.ACNT_ID=:ACNT_ID AND A.ACNT_PW=:ACNT_PW")
	public Account findAccount(@Param("ACNT_ID")String ACNT_ID, @Param("ACNT_PW")String ACNT_PW);

	//-------------------------------------------------------------------------------------------------------------------
	// 아이디와 비밀번호 값이 일치하는 계정정보 가져오기 (로그인) --> TripERP, 관리자웹페이지 통합 버전
	//-------------------------------------------------------------------------------------------------------------------
	@Query(nativeQuery=true, value="SELECT A.TABL_CNMB           AS TABL_CNMB,\n" +
								   "       A.CMPN_NO             AS CMPN_NO,\n" +
								   "       B.CMPN_NM             AS CMPN_NM,\n" +
								   "       A.EMPL_NO             AS EMPL_NO,\n" +
								   "       C.EMPL_NM             AS EMPL_NM,\n" +
								   "       C.DEPT_NM             AS DEPT_NM,\n" +
								   "       C.PSTN_CD             AS PSTN_CD,\n" +
								   "       D.CD_VLID_VAL_DESC    AS PSTN_NM,\n" +
								   "       A.ACNT_ID             AS ACNT_ID,\n" +
								   "       A.ACNT_PW             AS ACNT_PW,\n" +
								   "	   A.MAC				 AS MAC,\n"+	
								   "       A.CNNC_PRMI_YN        AS CNNC_PRMI_YN,\n" +
								   "       C.HLOF_DVSN_CD        AS HLOF_DVSN_CD,\n" +
								   "       E.CD_VLID_VAL_DESC    AS HLOF_DVSN_NM,\n" +
								   "       A.SCRN_SORT_ORD\n" +
								   "FROM   db_gbridge_trip.TB_ACNT_INFO_M          A,\n" +
								   "       db_gbridge_trip.TB_COOP_CMPN_M          B,\n" +
								   "       db_gbridge_trip.TB_EMPL_M               C,\n" +
								   "       db_gbridge_trip.TB_CMMN_CD_VLID_VAL_D   D,\n" +
								   "       db_gbridge_trip.TB_CMMN_CD_VLID_VAL_D   E\n" +
								   "WHERE A.ACNT_ID = :ACNT_ID \n" +
								   "  AND A.ACNT_PW = :ACNT_PW\n" +
								   "  AND B.CMPN_NO  =  A.CMPN_NO\n" +
								   "  AND C.EMPL_NO   =  A.EMPL_NO\n" +
								   "  AND D.DOMN_ENG_NM  =  'PSTN_CD'\n" +
								   "  AND D.CD_VLID_VAL  =   C.PSTN_CD\n" +
								   "  AND E.DOMN_ENG_NM  =  'HLOF_DVSN_CD'\n" +
								   "  AND E.CD_VLID_VAL  =   C.HLOF_DVSN_CD;")
	public Account findAccountInfo(@Param("ACNT_ID")String ACNT_ID, @Param("ACNT_PW")String ACNT_PW);

	
	
	
	
	
	//---------------------------------------------------------------
	// 사용자 계정 정보 변경 (비밀번호 변경 etc)
	//---------------------------------------------------------------
	@Transactional
	@Modifying
	@Query(nativeQuery=true, value="UPDATE TB_ACNT_INFO_M SET ACNT_PW=:ACNT_PW WHERE ACNT_ID=:ACNT_ID")
	public int changePassword(@Param("ACNT_PW")String newACNT_PW, @Param("ACNT_ID")String ACNT_ID);
}
