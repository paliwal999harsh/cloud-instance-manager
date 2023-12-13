package com.paliwal999harsh.cloudinstancemanager.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.paliwal999harsh.cloudinstancemanager.model.InstanceEntity;

import reactor.core.publisher.Mono;

@Repository
public interface InstanceRepo extends ReactiveCrudRepository<InstanceEntity, String>{

    Mono<InstanceEntity> findByInstanceName(String instanceName);

}
