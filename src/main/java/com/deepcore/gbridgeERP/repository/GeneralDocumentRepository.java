package com.deepcore.gbridgeERP.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.deepcore.gbridgeERP.model.entity.GeneralDocument;

/**
 * 
 * @author ParkHyeonho
 * 일반 문서 자료실 관련 TB Table TB_GNRL_DOCU_FILE 에 CRUD 작업을 수행하는 Repository Interface
 */

public interface GeneralDocumentRepository extends JpaRepository<GeneralDocument, Integer> {
	
	//-----------------------------------------------------------------
	// 일반 문서 모두 가져오기
	//-----------------------------------------------------------------
	@Query(nativeQuery=true, value="SELECT SEQ, FILE_NAME, PATH, UPLOADER, DATE, HIT, DSCPT FROM TB_GNRL_DOCU_FILE ORDER BY SEQ DESC")
	public List<GeneralDocument> findAll();
	
	
	
	
	//-----------------------------------------------------------------
	// 특정 일반 문서 검색!
	//-----------------------------------------------------------------
	@Query(nativeQuery=true, value="SELECT SEQ, FILE_NAME, PATH, UPLOADER, DATE, HIT, DSCPT FROM TB_GNRL_DOCU_FILE WHERE FILE_NAME=:FILE_NAME AND SEQ=:SEQ")
	public GeneralDocument searchGeneralDocument(@Param("FILE_NAME")String FILE_NAME, @Param("SEQ")int SEQ);
	
	
	//-----------------------------------------------------------------
	// 일반 문서 검색 조건에 맞추어 검색!
	//-----------------------------------------------------------------
	@Query(nativeQuery=true, value="CALL SelectGeneralDocument(:searchConditionValue, :searchWord)")
	public List<GeneralDocument> searchGeneralDocument(@Param("searchConditionValue")String searchConditionValue, @Param("searchWord")String searchWord);
	
	
	
	
	//-----------------------------------------------------------------
	// 일반 문서 등록!
	//-----------------------------------------------------------------
	@Transactional
	@Modifying
	@Query(nativeQuery=true, value="INSERT INTO TB_GNRL_DOCU_FILE(FILE_NAME, PATH, UPLOADER, DSCPT) VALUES(:FILE_NAME, :PATH, :UPLOADER, :DSCPT)")
	public int insertGeneralDocument(@Param("FILE_NAME")String FILE_NAME, @Param("PATH")String PATH, @Param("UPLOADER")String UPLOADER, @Param("DSCPT")String DSCPT);
	
	
	
	//-----------------------------------------------------------------
	// 일반 문서 삭제
	//-----------------------------------------------------------------
	@Transactional
	@Modifying
	@Query(nativeQuery=true, value="DELETE FROM TB_GNRL_DOCU_FILE WHERE SEQ=:SEQ AND FILE_NAME=:FILE_NAME")
	public int deleteGeneralDocument(@Param("FILE_NAME")String FILE_NAME, @Param("SEQ") int SEQ);
	
	
	
	
	
	//-----------------------------------------------------------------
	// 일반 문서 수정 (File 변경 없을시)
	//-----------------------------------------------------------------
	@Transactional
	@Modifying
	@Query(nativeQuery=true, value="UPDATE TB_GNRL_DOCU_FILE SET FILE_NAME=:FILE_NAME, DSCPT=:DSCPT WHERE SEQ=:SEQ")
	public int updateGeneralDocument(@Param("FILE_NAME")String FILE_NAME, @Param("DSCPT") String DSCPT, @Param("SEQ")int SEQ);
	
	//-----------------------------------------------------------------
	// 일반 문서 수정 (File 변경시)
	//-----------------------------------------------------------------
	@Transactional
	@Modifying
	@Query(nativeQuery=true, value="UPDATE TB_GNRL_DOCU_FILE SET FILE_NAME=:FILE_NAME, PATH=:PATH, DSCPT=:DSCPT WHERE SEQ=:SEQ")
	public int updateGeneralDocument(@Param("FILE_NAME")String FILE_NAME, @Param("DSCPT") String DSCPT, @Param("PATH")String PATH, @Param("SEQ")int SEQ);
	
	
	
	
	//-------------------------------------------------------------------
	// 일반문서 다운로드 횟수 증가
	//-------------------------------------------------------------------
	@Transactional
	@Modifying
	@Query(nativeQuery=true, value="UPDATE TB_GNRL_DOCU_FILE SET HIT=HIT+1 WHERE FILE_NAME=:FILE_NAME AND SEQ=:SEQ")
	public int updateHitGeneralDocument(@Param("FILE_NAME")String FILE_NAME, @Param("SEQ")int SEQ);
	
}
