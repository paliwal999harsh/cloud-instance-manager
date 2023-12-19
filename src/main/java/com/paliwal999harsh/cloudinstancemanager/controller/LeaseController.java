package com.paliwal999harsh.cloudinstancemanager.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paliwal999harsh.cloudinstancemanager.config.LeaseMapper;
import com.paliwal999harsh.cloudinstancemanager.model.InstanceEntity;
import com.paliwal999harsh.cloudinstancemanager.model.LeaseEntity;
import com.paliwal999harsh.cloudinstancemanager.repository.InstanceRepo;
import com.paliwal999harsh.cloudinstancemanager.service.AWSService;
import com.paliwal999harsh.cloudinstancemanager.view.LeaseView;

@RestController
@RequestMapping("cim/api/v1/lease")
@Validated
public class LeaseController{
    
    @Autowired
    AWSService awsService;

    @Autowired
    InstanceRepo instanceRepo;

    private final LeaseMapper mapper = Mappers.getMapper(LeaseMapper.class);
    /**
     * GET /lease : Read a lease
     *
     * @param instanceName the unique name of the instance (required)
     * @return Read a lease corresponding to the given &#39;instanceName&#39; (status code 200)
     */

    @GetMapping(
        value = "",
        produces = { "application/json" }
    )
    public ResponseEntity<LeaseView> getLease(
        @NotNull 
        @Size(max = 15) 
        @Valid @RequestParam(value = "instanceName", required = true) String instanceName) {
        
        InstanceEntity instance = instanceRepo.findByInstanceName(instanceName);
        if(instance==null){
            return ResponseEntity.notFound().build();
        }
        
        LeaseEntity lease = awsService.getLease(instanceName);
        return lease != null?
            ResponseEntity.ok(mapper.entityToView(lease)):
            ResponseEntity.internalServerError().build();

    }


    /**
     * PUT /lease/update : will create a lease, with the request body provided
     *
     * @param leaseRequest  (required)
     * @return request is valid, and server accepted it. (status code 202)
     */
    @PutMapping(
        value = "/update",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    public ResponseEntity<LeaseView> updateLease(
        @Valid @RequestBody LeaseView leaseRequest) {
        
        InstanceEntity instance = instanceRepo.findByInstanceName(leaseRequest.instanceName());
        if(instance==null){
            return ResponseEntity.notFound().build();
        }

        LeaseEntity lease = awsService.updateLease(leaseRequest);
        return lease !=null?
            ResponseEntity.accepted().body(mapper.entityToView(lease)) :
            ResponseEntity.unprocessableEntity().build(); 

    }
}
