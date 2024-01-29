package com.paliwal999harsh.cloudinstancemanager.microservices.lease.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paliwal999harsh.cloudinstancemanager.dto.InstanceDTO;
import com.paliwal999harsh.cloudinstancemanager.dto.LeaseDTO;
import com.paliwal999harsh.cloudinstancemanager.microservices.lease.config.LeaseMapper;
import com.paliwal999harsh.cloudinstancemanager.microservices.lease.model.LeaseEntity;
import com.paliwal999harsh.cloudinstancemanager.microservices.lease.service.LeaseService;

@RestController
@RequestMapping("cim/api/v1/lease")
@Validated
public class LeaseController{
    
    @Autowired
    LeaseService leaseService;

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
    public ResponseEntity<LeaseDTO> getLease(
        @NotNull 
        @Size(max = 15) 
        @Valid @RequestParam(value = "instanceName", required = true) String instanceName) {
        
        // InstanceDTO instance = instanceRepo.findByInstanceName(instanceName);
        // if(instance==null){
        //     return ResponseEntity.notFound().build();
        // }
        
        LeaseEntity lease = leaseService.getLease(instanceName);
        return lease != null?
            ResponseEntity.ok(mapper.entityToDto(lease)):
            ResponseEntity.internalServerError().build();

    }


    /**
     * PUT /lease : will update a lease, with the request body provided
     *
     * @param leaseRequest  (required)
     * @return request is valid, and server accepted it. (status code 202)
     */
    @PutMapping(
        value = "",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    @CrossOrigin
    public ResponseEntity<LeaseDTO> updateLease(
        @Valid @RequestBody LeaseDTO leaseRequest) {
        
        // InstanceDTO instance = instanceRepo.findByInstanceName(leaseRequest.instanceName());
        // if(instance==null){
        //     return ResponseEntity.notFound().build();
        // }

        LeaseEntity lease = leaseService.updateLease(leaseRequest);
        return lease !=null?
            ResponseEntity.accepted().body(mapper.entityToDto(lease)) :
            ResponseEntity.unprocessableEntity().build(); 

    }

    /**
     * POST /lease : will create a lease, with the request body provided
     *
     * @param leaseRequest  (required)
     * @return request is valid, and server accepted it. (status code 202)
     */
    @PostMapping(
        value = "",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    public ResponseEntity<LeaseDTO> createLease(
        @Valid @RequestBody InstanceDTO instance) {

        LeaseEntity lease = leaseService.createLease(instance);
        return lease !=null?
            ResponseEntity.accepted().body(mapper.entityToDto(lease)) :
            ResponseEntity.unprocessableEntity().build(); 

    }
}

