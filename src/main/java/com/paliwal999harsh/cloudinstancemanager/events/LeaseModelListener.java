package com.paliwal999harsh.cloudinstancemanager.events;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import com.paliwal999harsh.cloudinstancemanager.model.LeaseEntity;
import com.paliwal999harsh.cloudinstancemanager.service.SequenceGeneratorService;
import com.paliwal999harsh.cloudinstancemanager.service.TriggerGeneratorService;

@Component
public class LeaseModelListener extends AbstractMongoEventListener<LeaseEntity> {
    private SequenceGeneratorService sequenceGenerator;
    private TriggerGeneratorService triggerGenerator;

    @Autowired
    public LeaseModelListener(SequenceGeneratorService sequenceGenerator, TriggerGeneratorService triggerGenerator) {
        this.sequenceGenerator = sequenceGenerator;
        this.triggerGenerator = triggerGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<LeaseEntity> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(sequenceGenerator.generateSequence(LeaseEntity.SEQUENCE_NAME));
        }
    }

    @Override
    public void onBeforeSave(BeforeSaveEvent<LeaseEntity> event){
        event.getSource().setSysUpdatedOn(LocalDateTime.now());
    }
    
    @Override
    public void onAfterSave(AfterSaveEvent<LeaseEntity> event){
        triggerGenerator.generateStartAndStopActions(event.getSource());
    }
}
