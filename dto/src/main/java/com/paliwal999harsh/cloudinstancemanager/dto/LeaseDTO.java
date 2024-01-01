package com.paliwal999harsh.cloudinstancemanager.dto;

public record LeaseDTO(
    String instanceName,
    String startDate,
    String endDate,
    String startTime,
    String endTime,
    Boolean alwaysOn,
    Boolean weekendOn,
    String reason
) {
}