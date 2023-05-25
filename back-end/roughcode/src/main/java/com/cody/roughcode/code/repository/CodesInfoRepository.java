package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.entity.CodesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CodesInfoRepository extends JpaRepository<CodesInfo, Long> {

    CodesInfo findByCodes(Codes code);

    List<CodesInfo> findByCodesIn(List<Codes> codesList);

    @Query("select c from CodesInfo c where c.codes.codesId = :codesId and c.codes.expireDate is NULL ")
    CodesInfo findByCodesId(@Param("codesId") Long codesId);

    @Modifying
    @Transactional
    @Query("delete from CodesInfo c where c.codes IN :codesList")
    void deleteAllByCodesList(@Param("codesList") List<Codes> codesList);
}
