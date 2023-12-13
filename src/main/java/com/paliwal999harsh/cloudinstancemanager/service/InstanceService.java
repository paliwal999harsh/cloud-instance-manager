package com.paliwal999harsh.cloudinstancemanager.service;

import com.paliwal999harsh.cloudinstancemanager.model.InstanceEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InstanceService {
    Mono<InstanceEntity> createInstance(String instanceName);
    Flux<InstanceEntity> getAllInstances();
    Mono<Void> stopInstance(String instanceName);
    Mono<Void> startInstance(String instanceName);
    String getInstanceStatus(String instanceName);
}
