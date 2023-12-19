package com.paliwal999harsh.cloudinstancemanager.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class LeaseActionDTO {
    private String instanceName;
    private ZonedDateTime time;
    private Boolean alwaysOn;
    private Boolean weekendOn;
    private String action;
}
