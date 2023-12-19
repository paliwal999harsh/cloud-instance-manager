package com.paliwal999harsh.cloudinstancemanager.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Document(collection="instances")
@Data
@Validated
public class InstanceEntity{

    @Transient
    public static final String SEQUENCE_NAME = "instance_entity_sequence";

    @MongoId @NotEmpty
    private Long id;
    
    @Indexed(unique = true) @NotEmpty
    private final String instanceId;
    
    @Indexed(unique = true)
    private final String instanceName; 
    
    @NotEmpty @Valid
    private final CloudProvider cloud;

    @Version
    private Integer version;

}
