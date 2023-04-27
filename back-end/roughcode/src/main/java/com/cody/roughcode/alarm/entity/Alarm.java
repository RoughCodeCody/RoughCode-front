package com.cody.roughcode.alarm.entity;

import com.cody.roughcode.alarm.dto.req.AlarmReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("alarm")
public class Alarm {
    @Id
    private ObjectId id;
    String section; // project or code
    List<String> content;
    Long postId; // project or code id
    Long userId; // 알람 받을 id

    public Alarm(AlarmReq req) {
        this.content = req.getContent();
        this.section = req.getSection();
        this.postId = req.getPostId();
        this.userId = req.getUserId();
    }
}
