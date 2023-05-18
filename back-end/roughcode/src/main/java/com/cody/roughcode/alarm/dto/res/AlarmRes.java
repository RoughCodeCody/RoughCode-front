package com.cody.roughcode.alarm.dto.res;

import com.cody.roughcode.alarm.entity.Alarm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmRes {
    @Id
    private String alarmId;
    private String section; // project or code
    private List<String> content;
    private Long postId; // project or code id
    private Long userId; // 알람 받을 id
    private LocalDateTime createdDate;

    public AlarmRes(Alarm alarm) {
            this.createdDate = alarm.getCreatedDate();
            this.userId = alarm.getUserId();
            this.postId = alarm.getPostId();
            this.content = alarm.getContent();
            this.alarmId = alarm.getId().toHexString();
            this.section = alarm.getSection();
    }
}
