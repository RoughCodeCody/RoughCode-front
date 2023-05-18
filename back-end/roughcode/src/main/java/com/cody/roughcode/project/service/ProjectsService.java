package com.cody.roughcode.project.service;

import com.cody.roughcode.project.dto.req.FeedbackInsertReq;
import com.cody.roughcode.project.dto.req.FeedbackUpdateReq;
import com.cody.roughcode.project.dto.res.FeedbackInfoRes;
import com.cody.roughcode.project.dto.res.ProjectInfoRes;
import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.project.dto.res.ProjectDetailRes;
import com.cody.roughcode.project.dto.res.ProjectTagsRes;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface ProjectsService {
    Long insertProject(ProjectReq req, Long usersId) throws MessagingException, IOException;
    int updateProjectThumbnail(MultipartFile thumbnail, Long projectsId, Long usersId);
    String insertImage(MultipartFile image, Long projectsId, Long usersId);
    int deleteImage(String imgUrl, Long projectsId, Long usersId);
    int updateProject(ProjectReq req, Long usersId);
    int connect(Long projectsId, Long usersId, List<Long> codesIdList);
    int putExpireDateProject(Long projectsId, Long usersId);
    int deleteProject(Long projectsId, Long usersId);
    void deleteExpiredProject();
    Pair<List<ProjectInfoRes>, Boolean> getProjectList(String sort, PageRequest pageRequest, String keyword, String tagIds, int closed);
    ProjectDetailRes getProject(Long projectId, Long usersId);
    int likeProject(Long projectsId, Long usersId);
    int favoriteProject(Long projectsId, Long usersId);
    int openProject(Long projectsId, Long usersId);
    int closeProject(Long projectsId, Long usersId);
    int isProjectOpen(Long projectId) throws MessagingException, IOException;
    Boolean checkProject(String url, boolean open) throws IOException;

    int insertFeedback(FeedbackInsertReq req, Long usersId) throws MessagingException;
    Boolean updateFeedback(FeedbackUpdateReq req, Long userId);
    List<FeedbackInfoRes> getFeedbackList(Long projectId, Long usersId, boolean versionUp);

    int deleteFeedback(Long feedbackId, Long usersId);
    int feedbackComplain(Long feedbackId, Long usersId);
    int likeProjectFeedback(Long feedbackId, Long usersId);

    List<ProjectTagsRes> searchTags(String s);
}
