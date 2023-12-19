package com.paliwal999harsh.cloudinstancemanager.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paliwal999harsh.cloudinstancemanager.model.LeaseEntity;
import com.paliwal999harsh.cloudinstancemanager.model.Trigger;
import com.paliwal999harsh.cloudinstancemanager.repository.TriggerRepo;

@Service
public class TriggerGeneratorService {

    private final static String START_ACTION="START";
    private final static String STOP_ACTION="STOP";


    TriggerRepo triggerRepo;
    
    @Autowired
    TriggerGeneratorService(TriggerRepo triggerRepo){
        this.triggerRepo = triggerRepo;
    }
    
    public void generateStartAndStopActions(LeaseEntity lease) {
        LocalDate startDate = LocalDate.parse(lease.getStartDate(),DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(lease.getEndDate(),DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime startTime = LocalTime.parse(lease.getStartTime(), DateTimeFormatter.ISO_LOCAL_TIME);
        LocalTime endTime = LocalTime.parse(lease.getEndTime(), DateTimeFormatter.ISO_LOCAL_TIME);

        if(triggerRepo.findAllByInstanceName(lease.getInstanceName())!=null){
            triggerRepo.deleteAllByInstanceName(lease.getInstanceName());
        }

        Boolean alwaysOn = lease.getAlwaysOn();
        Boolean weekendOn = lease.getWeekendOn();

        String instanceName = lease.getInstanceName();

        if(Boolean.logicalAnd(alwaysOn, weekendOn)){
            triggerRepo.save(new Trigger(instanceName, startDate.atTime(startTime),START_ACTION,lease));
            triggerRepo.save(new Trigger(instanceName, endDate.atTime(endTime), STOP_ACTION,lease));
        }
        else if(Boolean.logicalAnd(alwaysOn,!weekendOn)){
            for(LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)){
                if(date.getDayOfWeek().equals(DayOfWeek.FRIDAY)){
                    triggerRepo.save(new Trigger(instanceName, date.atTime(endTime), STOP_ACTION,lease));
                }
                if(date.getDayOfWeek().equals(DayOfWeek.MONDAY)){
                    triggerRepo.save(new Trigger(instanceName, date.atTime(startTime), START_ACTION,lease));
                }
            }
        }
        else if(Boolean.logicalAnd(!alwaysOn,weekendOn)){
            for(LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)){
                triggerRepo.save(new Trigger(instanceName, date.atTime(startTime), START_ACTION,lease));
                triggerRepo.save(new Trigger(instanceName, date.atTime(endTime), STOP_ACTION,lease));
            }
        }
        else if(Boolean.logicalAnd(!alwaysOn,!weekendOn)){
            for(LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)){
                if(date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || 
                    date.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                    continue;
                }

                triggerRepo.save(new Trigger(instanceName, date.atTime(startTime), START_ACTION,lease));
                triggerRepo.save(new Trigger(instanceName, date.atTime(endTime), STOP_ACTION,lease));
            }
        }
    }
    
}
