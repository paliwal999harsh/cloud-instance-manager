package com.paliwal999harsh.cloudinstancemanager.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document(collection="lease")
@Data
public class LeaseEntity{
    @Id 
    @NotNull
    @Valid
    private final InstanceEntity instance;

    @NotNull
    private LocalDateTime startDateTime;
    
    @NotNull
    private LocalDateTime endDateTime;
    
    @NotNull
    private Boolean alwaysOn;
    
    @NotNull
    private Boolean weekendOn;

    @Version 
    private final Integer version;
}
