package com.paliwal999harsh.cloudinstancemanager.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Document(collection = "database_sequences")    
@Data    
public class DatabaseSequence {
    
    @MongoId
    private String id;

    private Long seq;
}
