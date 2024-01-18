package com.paliwal999harsh.cloudinstancemanager.util;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
public class SequenceGenerator {
    
    @Autowired
    private MongoOperations mongoOperations;

    public long generateSequence(String seqName) {

        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;

    }

    /**
     * Database Sequence
     */
    @Document(collection = "database_sequences")    
    @Data    
    public class DatabaseSequence {
        
        @MongoId
        private String id;
    
        private Long seq;
    }
}

