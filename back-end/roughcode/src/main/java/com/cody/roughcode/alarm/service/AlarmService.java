package com.cody.roughcode.alarm.service;

import com.cody.roughcode.alarm.dto.req.AlarmReq;
import com.cody.roughcode.alarm.entity.Alarm;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AlarmService {
    int insertAlarm(AlarmReq req);
    List<Alarm> getAlarmList(Long usersId);
}
