package com.paliwal999harsh.cloudinstancemanager.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.paliwal999harsh.cloudinstancemanager.model.SmartTrigger;

@Repository
public interface SmartTriggerRepo extends MongoRepository<SmartTrigger, Long>{

    void deleteAllByInstanceName(String instanceName);

    List<SmartTrigger> findAllByInstanceName(String instanceName);

    List<SmartTrigger> findByFireTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    
}
