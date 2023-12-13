package com.paliwal999harsh.cloudinstancemanager.view;

import java.time.LocalDateTime;

public record LeaseView(String instanceName, LocalDateTime startDateTime, LocalDateTime endDateTime, Boolean alwaysOn, Boolean weekendOn) {
    
}
