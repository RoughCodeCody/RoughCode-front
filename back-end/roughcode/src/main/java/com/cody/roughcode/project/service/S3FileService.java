package com.cody.roughcode.project.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3FileService {
    String upload(MultipartFile profile, String dirName, String fileName) throws Exception;
    void delete(String imageUrlString);
    void deleteAll(String prefix);
}