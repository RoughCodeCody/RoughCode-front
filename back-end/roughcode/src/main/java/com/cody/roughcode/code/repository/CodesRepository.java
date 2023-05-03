package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CodesRepository extends JpaRepository<Codes, Long> {
    Codes findByCodesId(Long id);

//    // @Query 어노테이션은 LIMIT 설정 불가능 > Pageable 사용하여 가장 최신 버전의 프로젝트 불러오기 수행
//    @Query("SELECT c FROM Codes c WHERE c.num =" +
//            "(SELECT c2.num FROM Codes c2 WHERE c2.codesId = :id) ORDER BY c.version DESC")
//    List<Codes> findLatestCodesByCodesId(@Param("id") Long id, Pageable pageable);
//
//    default Codes findLatestByCodesId(Long codeId)
//    {
//        return findLatestCodesByCodesId(codeId, PageRequest.of(0, 1)).get(0);
//    }

    // @Query 어노테이션은 LIMIT 설정 불가능 > Pageable 사용하여 가장 최신 버전의 프로젝트 불러오기 수행
    @Query("SELECT c FROM Codes c WHERE c.num = " +
            "(SELECT c2.num FROM Codes c2 WHERE c2.codesId = :codeId and c2.codeWriter.usersId = :userId) and c.codeWriter.usersId = :userId ORDER BY c.version DESC")
    List<Codes> findLatestCodesByCodesIdAndUsersId(@Param("codeId") Long codeId, @Param("userId") Long userId, Pageable pageable);

    default Codes findLatestByCodesIdAndUsersId(Long codeId, Long userId)
    {
        // codeId, userId가 일치하는 코드가 없다면 null 반환
        if(findLatestCodesByCodesIdAndUsersId(codeId, userId, PageRequest.of(0, 1))== null || findLatestCodesByCodesIdAndUsersId(codeId, userId, PageRequest.of(0, 1)).size()==0){
            return null;
        }
        return findLatestCodesByCodesIdAndUsersId(codeId, userId, PageRequest.of(0, 1)).get(0);
    }

    List<Codes> findByNumAndCodeWriter(Long num, Users codeWriter);

    @Query("SELECT c FROM Codes c WHERE c.version = (SELECT MAX(c2.version) FROM Codes c2 WHERE (c2.num = c.num AND c2.codeWriter = c.codeWriter)) " +
            "AND (LOWER(c.title) LIKE %:keyword% OR LOWER(c.codeWriter.name) LIKE %:keyword%)")
    Page<Codes> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);


    @Query("SELECT c FROM Codes c WHERE c.codeWriter.usersId = :userId AND c.version = (SELECT MAX(c2.version) FROM Codes c2 WHERE (c2.num = c.num AND c2.codeWriter = c.codeWriter))")
    Page<Codes> findAllByCodeWriter(@Param("userId") Long userId, Pageable pageable);

//    @Query("SELECT pf.projects FROM ProjectFavorites pf JOIN pf.projects p WHERE pf.users.usersId = :userId ORDER BY p.modifiedDate DESC")
//    Page<Projects> findAllMyFavorite(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT distinct r.codes FROM Reviews r JOIN r.codes c WHERE r.users.usersId = :userId ORDER BY r.codes.modifiedDate DESC")
    Page<Codes> findAllMyReviews(@Param("userId") Long userId, Pageable pageable);
}
