package com.paliwal999harsh.cloudinstancemanager.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.paliwal999harsh.cloudinstancemanager.model.Trigger;

@Repository
public interface TriggerRepo extends MongoRepository<Trigger, Long>{

    void deleteAllByInstanceName(String instanceName);

    List<Trigger> findAllByInstanceName(String instanceName);
    
}
