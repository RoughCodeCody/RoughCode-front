package com.cody.roughcode.alarm.entity;

import com.cody.roughcode.alarm.dto.req.AlarmReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("alarm")
@Slf4j
public class Alarm {
    @Id
    private ObjectId id;
    private String section; // project or code
    private List<String> content;
    private Long postId; // project or code id
    private Long userId; // 알람 받을 id
    private LocalDateTime createdDate;


    public Alarm(AlarmReq req) {
        this.content = req.getContent();
        this.section = req.getSection();
        this.postId = req.getPostId();
        this.userId = req.getUserId();
        this.createdDate = LocalDateTime.now();
    }
}
