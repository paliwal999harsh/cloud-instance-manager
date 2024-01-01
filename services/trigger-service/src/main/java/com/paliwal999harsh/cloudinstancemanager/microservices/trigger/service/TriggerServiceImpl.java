package com.paliwal999harsh.cloudinstancemanager.microservices.trigger.service;

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
import org.springframework.web.client.RestTemplate;

import com.paliwal999harsh.cloudinstancemanager.dto.LeaseDTO;
import com.paliwal999harsh.cloudinstancemanager.microservices.trigger.model.TriggerEntity;
import com.paliwal999harsh.cloudinstancemanager.microservices.trigger.repository.TriggersRepo;

@Service
public class TriggerServiceImpl implements TriggerService {

    private final static Logger log = LoggerFactory.getLogger(TriggerServiceImpl.class);

    private final static String START_ACTION="START";
    private final static String STOP_ACTION="STOP";

    @Value("${fixedRate.in.milliseconds}")
    private long fixedRateInMilliseconds;
    
    @Autowired
    private TriggersRepo triggerRepo;

    private RestTemplate restTemplate;
    
    public TriggerServiceImpl(){
        this.restTemplate = new RestTemplate();
    }
    public void generateStartAndStopActions(LeaseDTO lease) {
        LocalDate startDate = LocalDate.parse(lease.startDate(),DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(lease.endDate(),DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime startTime = LocalTime.parse(lease.startTime(), DateTimeFormatter.ISO_LOCAL_TIME);
        LocalTime endTime = LocalTime.parse(lease.endTime(), DateTimeFormatter.ISO_LOCAL_TIME);

        if(triggerRepo.findAllByInstanceName(lease.instanceName())!=null){
            triggerRepo.deleteAllByInstanceName(lease.instanceName());
        }

        Boolean alwaysOn = lease.alwaysOn();
        Boolean weekendOn = lease.weekendOn();

        String instanceName = lease.instanceName();

        //At first we have to start the instance regardless of any condition
        triggerRepo.save(new TriggerEntity(instanceName, startDate.atTime(startTime),START_ACTION));
        
        if(!Boolean.logicalAnd(alwaysOn,weekendOn)){
            for(LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)){
                if(Boolean.logicalAnd(alwaysOn,!weekendOn)){
                    if(date.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
                        triggerRepo.save(new TriggerEntity(instanceName, date.atTime(LocalTime.MIDNIGHT), STOP_ACTION));
                    }
                    if(date.getDayOfWeek().equals(DayOfWeek.MONDAY)){
                        triggerRepo.save(new TriggerEntity(instanceName, date.atTime(LocalTime.MIN), START_ACTION));
                    }
                }
                else if(Boolean.logicalAnd(!alwaysOn,weekendOn)){
                    triggerRepo.save(new TriggerEntity(instanceName, date.atTime(startTime), START_ACTION));
                    triggerRepo.save(new TriggerEntity(instanceName, date.atTime(endTime), STOP_ACTION));
                }
                else if(Boolean.logicalAnd(!alwaysOn,!weekendOn)){
                    if(date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || 
                        date.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                        continue;
                    }
                    
                    triggerRepo.save(new TriggerEntity(instanceName, date.atTime(startTime), START_ACTION));
                    triggerRepo.save(new TriggerEntity(instanceName, date.atTime(endTime), STOP_ACTION));
                }
            }
        }

        //Atlast we have to stop the instance
        triggerRepo.save(new TriggerEntity(instanceName, endDate.atTime(endTime), STOP_ACTION));
    }   

    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
    public void performSetStateActions(){
        Instant startInstant = Instant.now();
        Instant endInstant = startInstant.plusMillis(fixedRateInMilliseconds);

        LocalDateTime startDateTime = LocalDateTime.ofInstant(startInstant, ZoneId.systemDefault());
        LocalDateTime endDateTime = LocalDateTime.ofInstant(endInstant, ZoneId.systemDefault());
        log.info("Executing perform Set state actions from start date: {} to end date: {}",startDateTime,endDateTime);

        List<TriggerEntity> triggers = triggerRepo.findByFireTimeBetween(startDateTime,endDateTime);
        log.info("Found {} triggers", triggers.size());
        
        String instanceServiceUrl = "http://instance-service:8080/";
        triggers.forEach( trigger -> {
            switch(trigger.getAction()){
                case START_ACTION:
                    restTemplate.put(instanceServiceUrl+trigger.getInstanceName(),null);
                    // awsService.startInstance(trigger.getInstanceName());
                    log.info("Starting instance: {}...",trigger.getInstanceName());
                    break;
                case STOP_ACTION:
                    // awsService.stopInstance(trigger.getInstanceName());
                    log.info("Stopping instance: {}...",trigger.getInstanceName());
                    break;
                default:
                    log.info("Invalid case: {}",trigger.getAction());
            }
        });
    }
}
