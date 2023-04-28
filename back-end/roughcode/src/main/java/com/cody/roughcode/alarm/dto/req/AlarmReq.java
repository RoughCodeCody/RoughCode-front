package com.cody.roughcode.alarm.dto.req;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmReq {
    String section; // project or code
    List<String> content;
    Long postId; // project or code id
    Long userId; // 알람 받을 id
}
