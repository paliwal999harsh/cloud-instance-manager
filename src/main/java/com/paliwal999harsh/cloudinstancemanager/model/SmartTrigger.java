package com.paliwal999harsh.cloudinstancemanager.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Document(collection = "smart_triggers")
@Data
public class SmartTrigger {

    @Transient
    public static final String SEQUENCE_NAME = "smart_triggers_sequence";

    @MongoId
    private Long id;

    @NotEmpty
    private final String instanceName;

    private final LocalDateTime fireTime;

    private final String action;

    @DBRef
    private final LeaseEntity lease;

    @Indexed(expireAfterSeconds = 0)
    private LocalDateTime expireAt;

    @Version
    private Integer version;
}
