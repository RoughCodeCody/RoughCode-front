package com.cody.roughcode.alarm.service;

import com.cody.roughcode.alarm.dto.req.AlarmReq;

public interface AlarmService {
    int insertAlarm(AlarmReq req);
}
