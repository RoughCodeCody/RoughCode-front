package com.cody.roughcode.project.service;

import com.cody.roughcode.project.dto.req.ProjectReq;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3FileService {
    String upload(MultipartFile profile, String dirName, List<String> fileNames) throws Exception;
    boolean delete(String filePath, String dirName);
}