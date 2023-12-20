package com.paliwal999harsh.cloudinstancemanager.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.paliwal999harsh.cloudinstancemanager.model.LeaseEntity;
import com.paliwal999harsh.cloudinstancemanager.service.SequenceGeneratorService;
import com.paliwal999harsh.cloudinstancemanager.service.SmartTriggerService;

@Component
public class LeaseModelListener extends AbstractMongoEventListener<LeaseEntity> {
    private SequenceGeneratorService sequenceGenerator;
    private SmartTriggerService triggerService;

    @Autowired
    public LeaseModelListener(SequenceGeneratorService sequenceGenerator, SmartTriggerService triggerService) {
        this.sequenceGenerator = sequenceGenerator;
        this.triggerService = triggerService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<LeaseEntity> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(sequenceGenerator.generateSequence(LeaseEntity.SEQUENCE_NAME));
        }
    }

    @Override
    public void onAfterSave(AfterSaveEvent<LeaseEntity> event){
        triggerService.generateStartAndStopActions(event.getSource());
    }
}
