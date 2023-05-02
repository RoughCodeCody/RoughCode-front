package com.cody.roughcode.alarm.repository;

import com.cody.roughcode.alarm.entity.Alarm;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends MongoRepository<Alarm, String> {
    List<Alarm> findByUserIdOrderByCreatedDateDesc(Long userId);

    Alarm findById(ObjectId alarmId);
    void deleteById(ObjectId alarmId);
}