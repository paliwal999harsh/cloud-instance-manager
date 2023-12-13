package com.paliwal999harsh.cloudinstancemanager.service;

import com.paliwal999harsh.cloudinstancemanager.model.LeaseEntity;

public interface LeaseService {
    LeaseEntity updateLease(LeaseEntity lease);
    LeaseEntity getLease(String instanceName);
}
