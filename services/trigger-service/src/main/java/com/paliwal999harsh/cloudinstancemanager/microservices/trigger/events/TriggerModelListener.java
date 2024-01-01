package com.paliwal999harsh.cloudinstancemanager.microservices.trigger.events;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.paliwal999harsh.cloudinstancemanager.microservices.trigger.model.TriggerEntity;
import com.paliwal999harsh.cloudinstancemanager.util.SequenceGenerator;

@Component
public class TriggerModelListener extends AbstractMongoEventListener<TriggerEntity> {

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<TriggerEntity> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(sequenceGenerator.generateSequence(TriggerEntity.SEQUENCE_NAME));
        }

        LocalDateTime fireTime = event.getSource().getFireTime();
        LocalDateTime expireAt = fireTime.plusMinutes(5);
    
        event.getSource().setExpireAt(expireAt);
    }

}
