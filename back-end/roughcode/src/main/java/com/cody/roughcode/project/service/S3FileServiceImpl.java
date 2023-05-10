package com.cody.roughcode.project.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        imageUrlString = imageUrlString.replace("https://roughcode.s3.ap-northeast-2.amazonaws.com", "https://d2swdwg2kwda2j.cloudfront.net");

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
    @Override
    public void delete(String imgUrl) {
        // S3에서 삭제
        final String dirName = "project/content";
        log.info("삭제할 이미지 url : {}", imgUrl);
        Pattern tokenPattern = Pattern.compile("(?<=project/content/).*");
        Matcher matcher = tokenPattern.matcher(imgUrl);

        String temp = null;
        if (matcher.find()) {
            temp = matcher.group();
        }

        String originalName = URLDecoder.decode(temp);
        String filePath = dirName + "/" + originalName;
        log.info("originalName : {}", originalName);
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, filePath));
            log.info("deletion complete : {}", filePath);
        } catch (SdkClientException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteAll(String prefix) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucket)
                .withPrefix(prefix);
        ObjectListing objectListing;
        do {
            objectListing = amazonS3Client.listObjects(listObjectsRequest);
            List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
            for (S3ObjectSummary objectSummary : objectSummaries) {
                String key = objectSummary.getKey();
                amazonS3Client.deleteObject(bucket, key);
            }
            listObjectsRequest.setMarker(objectListing.getNextMarker());
        } while (objectListing.isTruncated());
    }
}