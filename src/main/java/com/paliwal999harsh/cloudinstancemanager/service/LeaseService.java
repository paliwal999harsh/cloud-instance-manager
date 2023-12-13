package com.paliwal999harsh.cloudinstancemanager.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.paliwal999harsh.cloudinstancemanager.model.InstanceEntity;
import com.paliwal999harsh.cloudinstancemanager.model.LeaseEntity;
import com.paliwal999harsh.cloudinstancemanager.view.LeaseView;
public interface LeaseService {
    LeaseEntity updateLease(LeaseView lease);
    LeaseEntity getLease(String instanceName);
    default LeaseEntity createLease(InstanceEntity instanceEntity){
        String startDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE).toString();
        String endDate = LocalDate.now().plusDays(1L).format(DateTimeFormatter.ISO_LOCAL_DATE).toString();
        return new LeaseEntity(instanceEntity,startDate,endDate,"09:00","18:00",false,false,null);
    }
}
