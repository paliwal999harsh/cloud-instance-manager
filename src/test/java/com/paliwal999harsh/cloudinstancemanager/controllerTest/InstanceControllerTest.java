package com.paliwal999harsh.cloudinstancemanager.controllerTest;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.paliwal999harsh.cloudinstancemanager.config.InstanceMapper;
import com.paliwal999harsh.cloudinstancemanager.controller.InstanceController;
import com.paliwal999harsh.cloudinstancemanager.model.CloudProvider;
import com.paliwal999harsh.cloudinstancemanager.model.InstanceEntity;
import com.paliwal999harsh.cloudinstancemanager.repository.InstanceRepo;
import com.paliwal999harsh.cloudinstancemanager.repository.LeaseRepo;
import com.paliwal999harsh.cloudinstancemanager.repository.TriggerRepo;
import com.paliwal999harsh.cloudinstancemanager.service.AWSService;
import com.paliwal999harsh.cloudinstancemanager.service.SequenceGeneratorService;
import com.paliwal999harsh.cloudinstancemanager.service.TriggerGeneratorService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = InstanceController.class)
@ActiveProfiles("test")
public class InstanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AWSService awsService;

    @MockBean
    private InstanceRepo instanceRepo;

    @MockBean
    private LeaseRepo leaseRepo;

    @MockBean
    private TriggerRepo triggerRepo;
    
    @MockBean
    private SequenceGeneratorService sequenceGenerator;

    @MockBean
    private TriggerGeneratorService triggerGenerator;

    @InjectMocks
    private InstanceController instanceController;

    @MockBean
    private InstanceMapper mapper;

    @BeforeAll
    public static void beforeAllTests(){
    }
    
    @BeforeEach
    public void beforeEachTest(){
        instanceRepo.deleteAll();
        instanceRepo.save(new InstanceEntity("SampleServer05", "SampleServer05", CloudProvider.AWS));
        System.out.println("instance count "+instanceRepo.count());
    }

    @AfterEach
    public void afterEachTest(){
        instanceRepo.deleteAll();
    }
    
    @AfterAll
    public static void afterAllTests(){
        
    }

    @Test
    public void testGetInstance_ValidInstanceName_Returns200() throws Exception {
        // Arrange
        String validInstanceName = "SampleServer05";
        InstanceEntity mockInstance = new InstanceEntity("SampleServer05","SampleServer05",CloudProvider.AWS);
        when(instanceRepo.findByInstanceName(validInstanceName)).thenReturn(mockInstance);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cim/api/v1/instances/{instanceName}", validInstanceName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetInstance_InvalidInstanceName_Returns404() throws Exception {
        // Arrange
        String invalidInstanceName = "SampleServer07";
        when(instanceRepo.findByInstanceName(invalidInstanceName)).thenReturn(null);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cim/api/v1/instances/{instanceName}", invalidInstanceName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
