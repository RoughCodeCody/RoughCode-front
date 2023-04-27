package com.cody.roughcode.alarm.service;


import com.cody.roughcode.alarm.dto.req.AlarmReq;
import com.cody.roughcode.alarm.entity.Alarm;
import com.cody.roughcode.alarm.repository.AlarmRepository;
import com.cody.roughcode.exception.NotMatchException;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;
    private final UsersRepository usersRepository;

    private final List<String> categoryList = List.of("project", "code");

    @Override
    @Transactional
    public int insertAlarm(AlarmReq req) {
        Users user = usersRepository.findByUsersId(req.getUserId());
        if(user == null) throw new NullPointerException("일치하는 유저가 존재하지 않습니다");

        if(!categoryList.contains(req.getSection())) throw new NotMatchException("잘못된 접근입니다");

        log.info("save to mongo : " + String.join(" ", req.getContent()));
        alarmRepository.save(new Alarm(req));
        return 1;
    }
}
