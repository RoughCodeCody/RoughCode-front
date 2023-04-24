package com.cody.roughcode.project.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.cody.roughcode.exception.DeletionFailException;
import com.cody.roughcode.project.dto.req.ProjectReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3FileServiceImpl implements S3FileService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 이미지 업로드 후 URL 리턴
    @Override
    public String upload(MultipartFile multipartFile, String dirName, String fileName) throws IOException {
        log.info("-----------upload method start-----------");
        log.info("file : {}, dirName : {}", multipartFile, dirName);

        // 파일 변환
        File uploadFile = convertToFile(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile에서 File로 변환에 실패했습니다"));

        // 파일명에 project 정보 같이 입력
        StringBuilder fileInfo = new StringBuilder(dirName + "/" + fileName);

        log.info("new file Name : {}", fileInfo);

        // S3로 업로드
        amazonS3Client.putObject(new PutObjectRequest(bucket, String.valueOf(fileInfo), uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        URL imageUrl = amazonS3Client.getUrl(bucket, String.valueOf(fileInfo));
        if (imageUrl == null) {
            throw new NullPointerException("이미지 저장에 실패했습니다");
        }
        String imageUrlString = imageUrl.toString();

        // 로컬 파일 삭제
        if (uploadFile.exists()) {
            if (uploadFile.delete()) {
                log.info("로컬에서 파일이 삭제 성공");
            } else {
                log.error("로컬에서 파일이 삭제 실패");
            }
        }

        log.info("return : {}", imageUrlString);

        return imageUrlString;
    }

    // multipartFile -> File 형식으로 변환 및 로컬에 저장
    private Optional<File> convertToFile(MultipartFile file) throws IOException {
        File uploadFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(uploadFile);
        fos.write(file.getBytes());
        fos.close();

        return Optional.of(uploadFile);
    }

    // 이미지 삭제 method
    public boolean delete(String imageUrlString) {
        try {
            URL imageUrl = new URL(imageUrlString);
            String key = imageUrl.getPath().substring(1);
            amazonS3Client.deleteObject(bucket, key);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DeletionFailException("이미지");
        }

        return true;
    }

}