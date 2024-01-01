package com.paliwal999harsh.cloudinstancemanager.microservices.trigger.service;

import com.paliwal999harsh.cloudinstancemanager.dto.LeaseDTO;

public interface TriggerService {
    public void generateStartAndStopActions(LeaseDTO lease);
    public void performSetStateActions();
}
