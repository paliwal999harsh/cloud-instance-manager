package com.paliwal999harsh.cloudinstancemanager.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.paliwal999harsh.cloudinstancemanager.model.SmartTrigger;
import com.paliwal999harsh.cloudinstancemanager.service.SequenceGeneratorService;

@Component
public class TriggerModelListener extends AbstractMongoEventListener<SmartTrigger> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public TriggerModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<SmartTrigger> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(sequenceGenerator.generateSequence(SmartTrigger.SEQUENCE_NAME));
        }
    }

}
