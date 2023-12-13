package com.paliwal999harsh.cloudinstancemanager.view;

public record LeaseView(String instanceName, String startDate, String endDate, String startTime, String endTime, Boolean alwaysOn, Boolean weekendOn) {
    
}
