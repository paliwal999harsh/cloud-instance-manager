package com.paliwal999harsh.cloudinstancemanager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Document(collection="instances")
@Data
public class InstanceEntity{

    @Id @NotEmpty
    private final String instanceId;
    
    @Indexed(unique = true)
    private final String instanceName; 
    
    @NotEmpty @Valid
    private final CloudProvider cloud;

    @Version
    private final Integer version;

}
