package com.paliwal999harsh.cloudinstancemanager.service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.paliwal999harsh.cloudinstancemanager.model.LeaseEntity;
import com.paliwal999harsh.cloudinstancemanager.model.SmartTrigger;
import com.paliwal999harsh.cloudinstancemanager.repository.SmartTriggerRepo;

@Service
public class SmartTriggerService {

    private final static Logger log = LoggerFactory.getLogger(SmartTriggerService.class);

    private final static String START_ACTION="START";
    private final static String STOP_ACTION="STOP";

    @Value("${fixedRate.in.milliseconds}")
    private long fixedRateInMilliseconds;
    
    private SmartTriggerRepo triggerRepo;

    private AWSService awsService;
    
    @Autowired
    SmartTriggerService(SmartTriggerRepo triggerRepo, AWSService awsService){
        this.triggerRepo = triggerRepo;
        this.awsService = awsService;
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

        //At first we have to start the instance regardless of any condition
        triggerRepo.save(new SmartTrigger(instanceName, startDate.atTime(startTime),START_ACTION,lease));
        
        if(!Boolean.logicalAnd(alwaysOn,weekendOn)){
            for(LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)){
                if(Boolean.logicalAnd(alwaysOn,!weekendOn)){
                    if(date.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
                        triggerRepo.save(new SmartTrigger(instanceName, date.atTime(LocalTime.MIDNIGHT), STOP_ACTION,lease));
                    }
                    if(date.getDayOfWeek().equals(DayOfWeek.MONDAY)){
                        triggerRepo.save(new SmartTrigger(instanceName, date.atTime(LocalTime.MIN), START_ACTION,lease));
                    }
                }
                else if(Boolean.logicalAnd(!alwaysOn,weekendOn)){
                    triggerRepo.save(new SmartTrigger(instanceName, date.atTime(startTime), START_ACTION,lease));
                    triggerRepo.save(new SmartTrigger(instanceName, date.atTime(endTime), STOP_ACTION,lease));
                }
                else if(Boolean.logicalAnd(!alwaysOn,!weekendOn)){
                    if(date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || 
                        date.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                        continue;
                    }
                    
                    triggerRepo.save(new SmartTrigger(instanceName, date.atTime(startTime), START_ACTION,lease));
                    triggerRepo.save(new SmartTrigger(instanceName, date.atTime(endTime), STOP_ACTION,lease));
                }
            }
        }

        //Atlast we have to stop the instance
        triggerRepo.save(new SmartTrigger(instanceName, endDate.atTime(endTime), STOP_ACTION,lease));
    }   

    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
    public void performSetStateActions(){
        Instant startInstant = Instant.now();
        Instant endInstant = startInstant.plusMillis(fixedRateInMilliseconds);

        LocalDateTime startDateTime = LocalDateTime.ofInstant(startInstant, ZoneId.systemDefault());
        LocalDateTime endDateTime = LocalDateTime.ofInstant(endInstant, ZoneId.systemDefault());
        log.info("Executing perform Set state actions from start date: {} to end date: {}",startDateTime,endDateTime);

        List<SmartTrigger> triggers = triggerRepo.findByFireTimeBetween(startDateTime,endDateTime);
        log.info("Found {} triggers", triggers.size());
        
        triggers.forEach( trigger -> {
            switch(trigger.getAction()){
                case START_ACTION:
                    awsService.startInstance(trigger.getInstanceName());
                    log.info("Starting instance: {}...",trigger.getInstanceName());
                    break;
                case STOP_ACTION:
                    awsService.stopInstance(trigger.getInstanceName());
                    log.info("Stopping instance: {}...",trigger.getInstanceName());
                    break;
                default:
                    log.info("Invalid case: {}",trigger.getAction());
            }
        });
    }
}
