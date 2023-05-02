package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.entity.CodesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CodesInfoRepository extends JpaRepository<CodesInfo, Long> {

    CodesInfo findByCodes(Codes code);

    @Query("select c from CodesInfo c where c.codes.codesId = :codesId")
    CodesInfo findByCodesId(@Param("codesId") Long codesId);

}
