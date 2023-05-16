package com.cody.roughcode.project.repository;

import com.cody.roughcode.config.Querydsl4RepositorySupport;
import com.cody.roughcode.project.entity.*;
import com.cody.roughcode.user.entity.Users;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cody.roughcode.project.entity.QProjectFavorites.projectFavorites;
import static com.cody.roughcode.project.entity.QProjectSelectedTags.projectSelectedTags;
import static com.cody.roughcode.project.entity.QProjectTags.projectTags;
import static com.cody.roughcode.project.entity.QProjects.*;
import static com.querydsl.jpa.JPAExpressions.selectDistinct;

@Repository
public class ProjectSelectedTagsQRepository extends Querydsl4RepositorySupport {
    public ProjectSelectedTagsQRepository() {
        super(ProjectSelectedTags.class);
    }

//     SELECT p FROM Projects p JOIN p.selectedTags pst WHERE
//                p.version = (SELECT MAX(p2.version) FROM Projects p2 WHERE (p2.num = p.num AND p2.projectWriter = p.projectWriter AND p.expireDate IS NULL)) AND
//                pst.tags.tagsId IN :tagIds
//                AND (LOWER(p.title) LIKE %:keyword% OR LOWER(p.introduction) LIKE %:keyword%)
//                AND p.expireDate IS NULL
//                GROUP BY p.projectsId
//                HAVING COUNT(DISTINCT pst.tags.tagsId) = :tagIdsSize
    public Page<Projects> findAllByKeywordAndTag(String keyword, List<Long> tagIds, Long tagIdsSize, Pageable pageable) {
        JPAQueryFactory queryFactory = getQueryFactory();
        JPAQuery<Projects> query = queryFactory.selectFrom(projects)
                .join(projects.selectedTags, projectSelectedTags)
                .where(
                        projects.version.eq(
                                JPAExpressions.select(projects.version.max())
                                        .from(projects)
                                        .where(
                                                projects.num.eq(projects.num),
                                                projects.projectWriter.eq(projects.projectWriter),
                                                projects.expireDate.isNull()
                                        )
                        ),
                        projectSelectedTags.tags.tagsId.in(tagIds),
                        projects.title.toLowerCase().likeIgnoreCase("%" + keyword.toLowerCase() + "%")
                                .or(projects.introduction.toLowerCase().likeIgnoreCase("%" + keyword.toLowerCase() + "%")),
                        projects.expireDate.isNull()
                )
                .groupBy(projects.projectsId)
                .having(projectSelectedTags.tags.tagsId.countDistinct().eq(tagIdsSize));
    
        long totalCount = query.fetchCount();
    
        List<Projects> projects = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    
        return new PageImpl<>(projects, pageable, totalCount);
    }

    // SELECT p FROM Projects p JOIN p.selectedTags pst WHERE
    //            p.version = (SELECT MAX(p2.version) FROM Projects p2 WHERE (p2.num = p.num AND p2.projectWriter = p.projectWriter AND p.expireDate IS NULL)) AND
    //            pst.tags.tagsId IN :tagIds
    //            AND p.closed = false
    //            AND (LOWER(p.title) LIKE %:keyword% OR LOWER(p.introduction) LIKE %:keyword%)
    //            GROUP BY p.projectsId
    //            HAVING COUNT(DISTINCT pst.tags.tagsId) = :tagIdsSize
}