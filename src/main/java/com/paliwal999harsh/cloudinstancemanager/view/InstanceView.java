package com.paliwal999harsh.cloudinstancemanager.view;

import com.paliwal999harsh.cloudinstancemanager.model.CloudProvider;

public record InstanceView(String instanceId, String instanceName, CloudProvider cloud) {
} 
