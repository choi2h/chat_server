package com.ffs.chat.repository;

import com.ffs.chat.model.ConnectionLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionLogRepository extends MongoRepository<ConnectionLog, String> {
}
