package com.paliwal999harsh.cloudinstancemanager.service;

import com.paliwal999harsh.cloudinstancemanager.model.LeaseEntity;

import reactor.core.publisher.Mono;

public interface LeaseService {
    Mono<LeaseEntity> updateLease(LeaseEntity lease);
    Mono<LeaseEntity> getLease(String instanceName);
}
