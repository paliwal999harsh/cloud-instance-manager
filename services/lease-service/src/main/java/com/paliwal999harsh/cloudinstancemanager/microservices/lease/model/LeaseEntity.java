package com.paliwal999harsh.cloudinstancemanager.microservices.lease.model;


import java.time.LocalDateTime;

import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document(collection="lease")
@Data
@Validated
public class LeaseEntity{

    @Transient
    public static final String SEQUENCE_NAME = "lease_entity_sequence";

    @MongoId @NotEmpty
    private Long id;

    @Indexed(unique = true)
    @NotNull @Valid @NotEmpty
    private final String instanceName;

    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Use yyyy-MM-dd.")
    private String startDate;
    
    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Use yyyy-MM-dd.")
    private String endDate;
    
    @NotNull
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Invalid time format. Use HH:mm.")
    private String startTime;

    @NotNull
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Invalid time format. Use HH:mm.")
    private String endTime;

    @NotNull
    private Boolean alwaysOn;
    
    @NotNull
    private Boolean weekendOn;

    @NotNull @Field(value="sys_created_on")
    @CreatedDate
    private LocalDateTime sysCreatedOn;

    @NotNull @Field(value="sys_updated_on")
    @LastModifiedDate
    private LocalDateTime sysUpdatedOn;

    @Version 
    private Integer version;
}
