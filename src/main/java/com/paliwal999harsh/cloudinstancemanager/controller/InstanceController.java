package com.paliwal999harsh.cloudinstancemanager.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paliwal999harsh.cloudinstancemanager.config.InstanceMapper;
import com.paliwal999harsh.cloudinstancemanager.dto.InstanceRequestDTO;
import com.paliwal999harsh.cloudinstancemanager.model.CloudProvider;
import com.paliwal999harsh.cloudinstancemanager.model.InstanceEntity;
import com.paliwal999harsh.cloudinstancemanager.repository.InstanceRepo;
import com.paliwal999harsh.cloudinstancemanager.service.AWSService;
import com.paliwal999harsh.cloudinstancemanager.view.InstanceView;

import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("cim/api/v1/instances")
@Validated
public class InstanceController{

    private static Logger log = LoggerFactory.getLogger(InstanceController.class);

    @Autowired
    AWSService awsService;

    @Autowired
    InstanceRepo instanceRepo;

    private final InstanceMapper mapper = Mappers.getMapper(InstanceMapper.class);
    /**
     * POST /instances/create : will create a instance, with the request body provided
     *
     * @param InstanceRequest  (required)
     * @return request is valid, and server accepted it. (status code 202)
     *         or invalid cloud type provided (status code 418)
     */
     @PostMapping(
         value = "/create",
         produces = { "application/json" },
         consumes = { "application/json" }
     )
    public ResponseEntity<Object> createInstance(
        @Valid @RequestBody InstanceRequestDTO instanceRequest) {

        if(!CloudProvider.isValid(instanceRequest.getCloud())){
            log.info("cloud type is not provided/valid, taking default 'AWS'.");
            instanceRequest.setCloud(CloudProvider.AWS);
        }

        if(instanceRequest.getInstanceName().isEmpty()){
            return ResponseEntity.badRequest().body("Instance name is not provided.");
        }

        InstanceEntity instance = null;
        switch (instanceRequest.getCloud()) {
            case AWS:
                instance = awsService.createInstance(instanceRequest.getInstanceName());
                break;
            case Azure:

            default:
                return ResponseEntity.status(418).build();
        }
        return instance !=null ? 
            ResponseEntity.created(null).body(mapper.entityToView(instance)) :
            ResponseEntity.badRequest().build();
    }

    /**
     * GET /instances/{instanceName} : Read a instance
     *
     * @param instanceName The unique name of the instance, max length 15 (required)
     * @return The instance corresponding to the provided &#x60;instanceName&#x60; (status code 200)
     *         or No instance found for the provided &#x60;instanceName&#x60; (status code 404)
     *         or Unexpected error (status code 500)
     */
    @GetMapping(
        value = "/{instanceName}",
        produces = { "application/json" }
    )
    public ResponseEntity<Object> getInstance(
        @Size(max = 15) 
        @PathVariable("instanceName") String instanceName){
        
        InstanceEntity instance = instanceRepo.findByInstanceName(instanceName);
        return instance != null ?
            ResponseEntity.ok(mapper.entityToView(instance)):
            ResponseEntity.notFound().build();
    }
    
    /**
     * PUT /instances/{instanceName} : set the state of instance
     *
     * @param instanceName The unique name of the instance, max length 15 (required)
     * @param setState the state of the instance, to start or stop the instance (optional)
     * @return the instance state has been set to given &#39;state&#39; (status code 200)
     *         or the provided state is invalid (status code 400)
     *         or No instance found for the provided &#x60;instanceName&#x60; (status code 404)
     */

    @PutMapping(
        value = "/{instanceName}",
        produces = { "application/json" }
    )
    public ResponseEntity<String> changeInstanceState(
        @Size(max = 15) 
        @PathVariable("instanceName") String instanceName,
        @Valid @RequestParam(value = "setState", required = false) String setState){
            InstanceEntity instance = instanceRepo.findByInstanceName(instanceName);
            if(instance==null){
                return ResponseEntity.notFound().build();
            }
            String currentState = awsService.getInstanceStatus(instanceName);
            if(currentState.contentEquals(setState)){
                log.info("Instance is already in set state");
                return ResponseEntity.ok("Instance is already in set state");
            }
            else{
                switch (setState) {
                    case "START":
                        awsService.startInstance(instanceName);
                        break;
                    case "STOP":
                        awsService.stopInstance(instanceName);
                        break;
                    default:
                        return ResponseEntity.status(400).build();
                }
            }
            return ResponseEntity.ok("Instance has been set to given state");
    }
    
    /**
     * DELETE /instances/{instanceName} : deletes the instance
     *
     * @param instanceName The unique name of the instance, max length 15 (required)
     * @return Method not allowed (status code 405)
     */
    @DeleteMapping(
        value = "/{instanceName}",
        produces = { "application/json" }
    )
    public ResponseEntity<String> deleteInstance(
        @Size(max = 15) 
        @PathVariable("instanceName") String instanceName) {

        return ResponseEntity.status(405).body("Action Not Allowed");
    }

    /**
     * GET /instances/listInstances : Read all the instances
     *
     * @param cloud specify the cloud provider, i.e either AWS or Azure (optional, default to AWS)
     * @return list all instances available in the cloud (status code 200)
     *         or no instances are available in the cloud (status code 204)
     *         or invalid cloud provider provided (status code 400)
     */
    @GetMapping(
        value = "/listInstances",
        produces = { "application/json" }
    )
    public ResponseEntity<Object> listInstances(
        @Valid 
        @RequestParam(value = "cloudProvider", required = false, defaultValue = "AWS") CloudProvider cloud){
        List<InstanceView> instances = mapper.entityListToViewList(awsService.getAllInstances());  
        return instances != null ?
            ResponseEntity.ok().body(instances) :  
            ResponseEntity.noContent().build();
        //TODO when instances are terminated, the document remains in the collection, and it list that instance to
        //TODO When we try to put a terminated instance to some state it throws exception, unhandled, 500 Internal Server Exception 
    }

    @GetMapping(
        value = "/getInstanceStatus/{instanceName}",
        produces = {"application/json"})
    public ResponseEntity<Object> getInstanceStatus(@PathVariable @NotBlank String instanceName){
        String instanceStatus = awsService.getInstanceStatus(instanceName);
        return instanceStatus != null ?
            ResponseEntity.ok().body(instanceStatus) :
            ResponseEntity.notFound().build();
    }
}
