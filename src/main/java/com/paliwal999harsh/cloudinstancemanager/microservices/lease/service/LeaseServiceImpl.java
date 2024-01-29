package com.paliwal999harsh.cloudinstancemanager.microservices.lease.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paliwal999harsh.cloudinstancemanager.dto.InstanceDTO;
import com.paliwal999harsh.cloudinstancemanager.dto.LeaseDTO;
import com.paliwal999harsh.cloudinstancemanager.microservices.lease.model.LeaseEntity;
import com.paliwal999harsh.cloudinstancemanager.microservices.lease.repository.LeaseRepo;
/**
 * Service interface for managing leases associated with instances.
 */
@Service
public class LeaseServiceImpl implements LeaseService {

    @Autowired
    private LeaseRepo leaseRepo;

    /**
     * Updates the lease information for a given instance.
     *
     * @param leaseRequest The view containing updated lease information.
     * @return The updated LeaseEntity.
     */
    public LeaseEntity updateLease(LeaseDTO leaseRequest){
        LeaseEntity lease = leaseRepo.findLeaseByInstanceName(leaseRequest.instanceName());
        lease.setAlwaysOn(leaseRequest.alwaysOn());
        lease.setWeekendOn(leaseRequest.weekendOn());
        lease.setStartDate(leaseRequest.startDate());
        lease.setEndDate(leaseRequest.endDate());
        lease.setStartTime(leaseRequest.startTime());
        lease.setEndTime(leaseRequest.endTime());
        return leaseRepo.save(lease);
    }

    /**
     * Retrieves the lease information for a specific instance.
     *
     * @param instanceName The name of the instance.
     * @return The LeaseEntity for the specified instance.
     */
    public LeaseEntity getLease(String instanceName){
        return leaseRepo.findLeaseByInstanceName(instanceName);
    }
    
    /**
     * Creates a default lease for a new instance.
     * Default values:
     * - Start and end dates: EPOCH
     * - Start and end times: MIN and NOON
     * - AlwaysOn and WeekendOn: FALSE
     * - SysCreatedOn and SysUpdatedOn: Current timestamp
     *
     * @param instanceEntity The instance for which the lease is created.
     * @return A new LeaseEntity with default values.
     */
    public LeaseEntity createLease(InstanceDTO instanceEntity){
        LeaseEntity lease = new LeaseEntity(instanceEntity.instanceName());
        lease.setStartDate(LocalDate.EPOCH.format(DateTimeFormatter.ISO_LOCAL_DATE));
        lease.setEndDate(LocalDate.EPOCH.format(DateTimeFormatter.ISO_LOCAL_DATE));
        lease.setStartTime(LocalTime.MIN.toString());
        lease.setEndTime(LocalTime.NOON.toString());
        lease.setAlwaysOn(Boolean.FALSE);
        lease.setWeekendOn(Boolean.FALSE);
        lease.setSysCreatedOn(LocalDateTime.now());
        lease.setSysUpdatedOn(LocalDateTime.now());
        return leaseRepo.save(lease);
    }
}

