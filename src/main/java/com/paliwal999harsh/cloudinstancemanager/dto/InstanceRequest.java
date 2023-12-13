package com.paliwal999harsh.cloudinstancemanager.dto;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.paliwal999harsh.cloudinstancemanager.model.CloudProvider;

import lombok.Data;

@Data
public class InstanceRequestDTO {
    
    @Size(max = 15) 
    private String instanceName;
    
    @Valid
    private CloudProvider cloud = CloudProvider.AWS;
}
