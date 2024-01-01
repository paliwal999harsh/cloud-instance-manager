package com.paliwal999harsh.cloudinstancemanager.microservices.trigger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paliwal999harsh.cloudinstancemanager.dto.LeaseDTO;
import com.paliwal999harsh.cloudinstancemanager.microservices.trigger.service.TriggerService;


@RestController
@RequestMapping("/cim/api/v1/trigger")
@Validated
public class TriggerContoller {
    
    @Autowired
    TriggerService triggerService;

    @PostMapping("/generateActions")
    public ResponseEntity<Object> generateActions(@RequestBody LeaseDTO lease) {
        
        triggerService.generateStartAndStopActions(lease);
        return ResponseEntity.ok().body("Action Performed");
    }
    
}
