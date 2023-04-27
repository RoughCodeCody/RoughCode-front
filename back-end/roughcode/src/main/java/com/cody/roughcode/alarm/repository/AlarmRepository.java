package com.cody.roughcode.alarm.repository;

import com.cody.roughcode.alarm.entity.Alarm;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends MongoRepository<Alarm, String> {
}