package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.Codes;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CodesRepository extends JpaRepository<Codes, Long> {
    Codes findByCodesId(Long id);

    // @Query 어노테이션은 LIMIT 설정 불가능 > Pageable 사용하여 가장 최신 버전의 프로젝트 불러오기 수행
    @Query("SELECT c FROM Codes c WHERE c.num =" +
            "(SELECT c2.num FROM Codes c2 WHERE c2.codesId = :id) ORDER BY c.version DESC")
    List<Codes> findLatestCodesByCodesId(@Param("id") Long id, Pageable pageable);

    default Codes findLatestByCodesId(Long codeId)
    {
        return findLatestCodesByCodesId(codeId, PageRequest.of(0, 1)).get(0);
    }
}
