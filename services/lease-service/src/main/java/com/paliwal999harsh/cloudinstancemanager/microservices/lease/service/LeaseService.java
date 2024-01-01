package com.paliwal999harsh.cloudinstancemanager.microservices.lease.service;

import com.paliwal999harsh.cloudinstancemanager.dto.InstanceDTO;
import com.paliwal999harsh.cloudinstancemanager.dto.LeaseDTO;
import com.paliwal999harsh.cloudinstancemanager.microservices.lease.model.LeaseEntity;

public interface LeaseService {
    public LeaseEntity updateLease(LeaseDTO leaseRequest);
    public LeaseEntity getLease(String instanceName);
    public LeaseEntity createLease(InstanceDTO instanceEntity);
}
