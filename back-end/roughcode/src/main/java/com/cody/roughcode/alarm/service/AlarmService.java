package com.cody.roughcode.alarm.service;

import com.cody.roughcode.alarm.dto.req.AlarmReq;
import com.cody.roughcode.alarm.entity.AlarmRes;

import java.util.List;

public interface AlarmService {
    int insertAlarm(AlarmReq req);
    List<AlarmRes> getAlarmList(Long usersId);

    void deleteAlarm(String alarmId, Long usersId);
}
