package com.deepcore.gbridgeERP.repository;

import java.util.List;

import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.deepcore.gbridgeERP.model.entity.File;

/***
 * @Repository
 * @author ParkHyeonho
 *
 * DB 의 File Table 에 대한 CRUD 작업을 수행할 Repository
 * JPA 를 사용한다.
 */
public interface FileRepository extends JpaRepository<File, Integer> {
	
	//--------------------------------------------------------------------------------------------------------------------------
	// File 정보를 FileName, seq 를 통해 가져옴
	//--------------------------------------------------------------------------------------------------------------------------
	@Query(nativeQuery=true, value="SELECT seq, file_kind, cmpn_no, prdt_cnmb, file_name, path, uploader, date, hit FROM tb_file WHERE file_name=:fileName AND seq=:seq")
	public File findByFileNameAndSeq(@Param("fileName")String fileName, @Param("seq")int seq);
	
	
	//--------------------------------------------------------------------------------------------------------------------------
	// File 정보를 검색조건을 통해 가져옴
	//--------------------------------------------------------------------------------------------------------------------------
	@Query(nativeQuery=true, value="CALL SelectUploadFileList(:fileKind, :CMPN_NO, :PRDT_CNMB)")
	public List<File> findAll(@Param("fileKind")String fileKind, @Param("CMPN_NO") String CMPN_NO, @Param("PRDT_CNMB") String IN_PRDT_CNMB);
	
	
	
	//--------------------------------------------------------------------------------------------------------------------------
	// File Table 내부의 모든 Record 를 반환
	//--------------------------------------------------------------------------------------------------------------------------
	@Query(nativeQuery=true, value="SELECT seq, file_kind, cmpn_no, prdt_cnmb, file_name, path, uploader, date, hit FROM tb_file ORDER BY seq DESC")
	public List<File> findAll();		
	
	
	
	//--------------------------------------------------------------------------------------------------------------------------
	// File Table 내부의 특정 Record 를 삭제
	//--------------------------------------------------------------------------------------------------------------------------
	@Transactional
	@Modifying
	@Query(nativeQuery=true, value="DELETE FROM tb_file WHERE file_name=:fileName AND seq=:seq")
	public int deleteByFileName(@Param("fileName")String fileName, @Param("seq") int seq);
	
	
	
	
	//--------------------------------------------------------------------------------------------------------------------------
	// File Table 내부의 특정 File Download 횟수 정보인 hit 를 update
	//--------------------------------------------------------------------------------------------------------------------------
	@Transactional
	@Modifying
	@Query(nativeQuery=true, value="UPDATE tb_file SET hit=hit+1 WHERE seq=:seq AND file_name=:fileName")
	public int updateHit(@Param("fileName") String fileName, @Param("seq") int seq);
}
