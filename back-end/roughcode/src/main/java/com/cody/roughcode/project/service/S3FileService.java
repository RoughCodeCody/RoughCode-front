package com.cody.roughcode.project.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3FileService {
    String upload(MultipartFile profile, String dirName, String fileName) throws Exception;

    // 이미지 삭제 method
    void delete(String imgUrl, String dirName);

    void deleteAll(String prefix);
}