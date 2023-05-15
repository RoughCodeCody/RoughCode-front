package com.cody.roughcode.alarm.service;


import com.cody.roughcode.alarm.dto.req.AlarmReq;
import com.cody.roughcode.alarm.entity.Alarm;
import com.cody.roughcode.alarm.entity.AlarmRes;
import com.cody.roughcode.alarm.repository.AlarmRepository;
import com.cody.roughcode.exception.NotMatchException;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;
    private final UsersRepository usersRepository;

    private final List<String> categoryList = List.of("project", "code");

    @Override
    @Transactional
    public void insertAlarm(AlarmReq req) {
        Users user = usersRepository.findByUsersId(req.getUserId());
        if(user == null) throw new NullPointerException("일치하는 유저가 존재하지 않습니다");

        if(!categoryList.contains(req.getSection())) throw new NotMatchException("잘못된 접근입니다");

        log.info("save to mongo : " + String.join(" ", req.getContent()));
        alarmRepository.save(new Alarm(req));
    }

    @Override
    @Transactional
    public List<AlarmRes> getAlarmList(Long usersId) {
        Users user = usersRepository.findByUsersId(usersId);
        if(user == null) throw new NullPointerException("일치하는 유저가 존재하지 않습니다");

        return getAlarmRes(alarmRepository.findByUserIdOrderByCreatedDateDesc(usersId));
    }

    private List<AlarmRes> getAlarmRes(List<Alarm> alarmList) {
        List<AlarmRes> res = new ArrayList<>();
        for (Alarm alarm : alarmList) {
            res.add(new AlarmRes(alarm));
        }
        return res;
    }

    @Override
    @Transactional
    public void deleteAlarm(String alarmId, Long usersId) {
        Users user = usersRepository.findByUsersId(usersId);
        if(user == null) throw new NullPointerException("일치하는 유저가 존재하지 않습니다");

        Alarm alarm = alarmRepository.findById(new ObjectId(alarmId));
        if(alarm == null) throw new NullPointerException("일치하는 알림이 없습니다");
        if(!Objects.equals(alarm.getUserId(), user.getUsersId())) throw new NotMatchException();

        alarmRepository.deleteById(new ObjectId(alarmId));
    }

    private final EntityManager entityManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteLimited() {
        LocalDateTime tenDaysAgo = LocalDateTime.now().minusDays(10);
        alarmRepository.deleteOlderThan(tenDaysAgo);

        // 영속성 컨텍스트 초기화
        entityManager.clear();
    }
}
