package com.paliwal999harsh.cloudinstancemanager.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.paliwal999harsh.cloudinstancemanager.model.InstanceEntity;
import com.paliwal999harsh.cloudinstancemanager.model.LeaseEntity;

import reactor.core.publisher.Mono;

@Repository
public interface LeaseRepo extends ReactiveCrudRepository<LeaseEntity, InstanceEntity>{

    @Query("{ 'instanceName'} : ?0 ")
    Mono<LeaseEntity> findLeaseByInstanceName(String instanceName);
}
