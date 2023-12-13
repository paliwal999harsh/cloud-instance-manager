package com.paliwal999harsh.cloudinstancemanager.model;

import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Document(collection="lease")
@Data
@AllArgsConstructor
public class LeaseEntity{
    @Id 
    @NotNull
    @Valid
    private final InstanceEntity instance;

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

    @Version 
    private final Integer version;
}
