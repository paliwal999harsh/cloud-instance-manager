package com.paliwal999harsh.cloudinstancemanager.microservices.trigger.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.paliwal999harsh.cloudinstancemanager.microservices.trigger.model.TriggerEntity;

@Repository
public interface TriggersRepo extends MongoRepository<TriggerEntity, Long>{

    void deleteAllByInstanceName(String instanceName);

    List<TriggerEntity> findAllByInstanceName(String instanceName);

    List<TriggerEntity> findByFireTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    
}