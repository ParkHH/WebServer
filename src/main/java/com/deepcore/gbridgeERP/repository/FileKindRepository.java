package com.deepcore.gbridgeERP.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deepcore.gbridgeERP.model.entity.FileKind;

/**
 * 
 * @author ParkHyeonho
 * File 자료실 게시판의 파일 정보 SelectBox Option 을 채울 정보를 조회
 */
public interface FileKindRepository extends JpaRepository<FileKind, String> {
	
	//-------------------------------------------------
	// 모든 문서 종류 Data 불러오기!
	//-------------------------------------------------
	@Query(nativeQuery=true, value="SELECT CD_VLID_VAL, CD_VLID_VAL_DESC FROM TB_CMMN_CD_VLID_VAL_D WHERE DOMN_ENG_NM = \"DOCU_CLSF_CD\"")
	public List<FileKind> findAll();
}
