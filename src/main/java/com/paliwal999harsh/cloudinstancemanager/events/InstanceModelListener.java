package com.paliwal999harsh.cloudinstancemanager.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.paliwal999harsh.cloudinstancemanager.model.InstanceEntity;
import com.paliwal999harsh.cloudinstancemanager.service.SequenceGeneratorService;

@Component
public class InstanceModelListener extends AbstractMongoEventListener<InstanceEntity> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public InstanceModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<InstanceEntity> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(sequenceGenerator.generateSequence(InstanceEntity.SEQUENCE_NAME));
        }
    }
}
