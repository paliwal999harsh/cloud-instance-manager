package com.paliwal999harsh.cloudinstancemanager.service;

import java.util.List;

import com.paliwal999harsh.cloudinstancemanager.model.InstanceEntity;
public interface InstanceService {
    InstanceEntity createInstance(String instanceName);
    List<InstanceEntity> getAllInstances();
    void stopInstance(String instanceName);
    void startInstance(String instanceName);
    String getInstanceStatus(String instanceName);
}
