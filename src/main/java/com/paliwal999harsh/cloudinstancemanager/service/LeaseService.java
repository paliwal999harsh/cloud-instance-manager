package com.paliwal999harsh.cloudinstancemanager.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.paliwal999harsh.cloudinstancemanager.model.InstanceEntity;
import com.paliwal999harsh.cloudinstancemanager.model.LeaseEntity;
import com.paliwal999harsh.cloudinstancemanager.view.LeaseView;
public interface LeaseService {
    LeaseEntity updateLease(LeaseView lease);
    LeaseEntity getLease(String instanceName);
    default LeaseEntity createLease(InstanceEntity instanceEntity){
        LeaseEntity lease = new LeaseEntity(instanceEntity.getInstanceName());
        lease.setStartDate(LocalDate.EPOCH.format(DateTimeFormatter.ISO_LOCAL_DATE));
        lease.setEndDate(LocalDate.EPOCH.format(DateTimeFormatter.ISO_LOCAL_DATE));
        lease.setStartTime(LocalTime.MIN.toString());
        lease.setEndTime(LocalTime.NOON.toString());
        lease.setAlwaysOn(Boolean.FALSE);
        lease.setWeekendOn(Boolean.FALSE);
        lease.setSysCreatedOn(LocalDateTime.now());
        lease.setSysUpdatedOn(LocalDateTime.now());
        return lease;
    }
}
