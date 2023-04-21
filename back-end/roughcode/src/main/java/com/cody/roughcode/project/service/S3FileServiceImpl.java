package com.cody.roughcode.project.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.cody.roughcode.project.dto.req.ProjectReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public String upload(MultipartFile multipartFile, String dirName, List<String> fileNames) throws IOException {
        log.info("-----------upload method start-----------");
        log.info("file : {}, dirName : {}", multipartFile, dirName);

        // 파일 변환
        File uploadFile = convertToFile(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile에서 File로 변환에 실패했습니다."));

        // 파일명에 project 정보 같이 입력
        StringBuilder fileName = new StringBuilder(dirName + "/project");
        for(String str : fileNames){
            fileName.append(str);
            fileName.append("_");
        }
        fileName.deleteCharAt(fileName.length() - 1);

        log.info("new file Name : {}", fileName);

        // S3로 업로드
        amazonS3Client.putObject(new PutObjectRequest(bucket, String.valueOf(fileName), uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        
        String uploadImageUrl = amazonS3Client.getUrl(bucket, String.valueOf(fileName)).toString();
        
        // 로컬 파일 삭제
        if (uploadFile.exists()) {
            if (uploadFile.delete()) {
                log.info("로컬에서 파일이 삭제 성공");
            } else {
                log.error("로컬에서 파일이 삭제 실패");
            }
        }

        return uploadImageUrl;
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
    @Override
    public boolean delete(String profileUrl, String dirName) {
        log.info("profile url : {}", profileUrl);
        
        // S3에서 이미지 검색
        Pattern tokenPattern = Pattern.compile("(?<=profile/).*");
        Matcher matcher = tokenPattern.matcher(profileUrl);

        String foundImage = null;
        if (matcher.find()) {
            foundImage = matcher.group();
        }

        String originalName = URLDecoder.decode(foundImage);
        String filePath = dirName + "/" + originalName;
        log.info("originalName : {}", originalName);
        
        // S3에서 이미지 삭제
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, filePath));
            log.info("deletion complete : {}", filePath);
            return true;
        } catch (SdkClientException e) {
            log.error(e.getMessage());
            return false;
        }
    }

}