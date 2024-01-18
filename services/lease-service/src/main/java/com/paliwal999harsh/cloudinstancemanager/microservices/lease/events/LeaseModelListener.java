package com.paliwal999harsh.cloudinstancemanager.microservices.lease.events;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.paliwal999harsh.cloudinstancemanager.microservices.lease.config.LeaseMapper;
import com.paliwal999harsh.cloudinstancemanager.microservices.lease.model.LeaseEntity;
import com.paliwal999harsh.cloudinstancemanager.util.SequenceGenerator;

@Component
public class LeaseModelListener extends AbstractMongoEventListener<LeaseEntity> {
    
    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private LeaseMapper mapper;
    
    private RestTemplate restTemplate;
    
    public LeaseModelListener() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<LeaseEntity> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(sequenceGenerator.generateSequence(LeaseEntity.SEQUENCE_NAME));
        }
    }

    @Override
    public void onAfterSave(AfterSaveEvent<LeaseEntity> event){
        String triggerServiceUrl = "http://trigger-service:8080/cim/api/v1/trigger/generateActions";
        restTemplate.postForEntity(triggerServiceUrl, mapper.entityToDto(event.getSource()), Void.class);
    }
}
