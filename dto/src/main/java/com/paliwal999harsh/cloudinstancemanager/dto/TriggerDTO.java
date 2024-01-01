package com.paliwal999harsh.cloudinstancemanager.dto;

import java.time.LocalDateTime;

/**
 * TriggerDTO
 */
public record TriggerDTO(
    String instanceName,
    LocalDateTime fireTime,
    String action,
    String leaseId
) {
}
