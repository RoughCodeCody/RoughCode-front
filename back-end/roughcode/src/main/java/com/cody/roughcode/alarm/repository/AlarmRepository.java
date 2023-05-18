package com.cody.roughcode.alarm.repository;

import com.cody.roughcode.alarm.entity.Alarm;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlarmRepository extends MongoRepository<Alarm, String> {
    List<Alarm> findByUserIdOrderByCreatedDateDesc(Long userId);

    List<Alarm> findByUserId(Long userId);
    Alarm findById(ObjectId alarmId);
    void deleteById(ObjectId alarmId);

    @DeleteQuery("{ 'createdDate' : { $lte: ?0 } }")
    void deleteOlderThan(LocalDateTime date);
}