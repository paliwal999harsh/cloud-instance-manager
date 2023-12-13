package com.paliwal999harsh.cloudinstancemanager.config;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.paliwal999harsh.cloudinstancemanager.model.LeaseEntity;
import com.paliwal999harsh.cloudinstancemanager.view.LeaseView;

@Mapper(componentModel = "spring")
public interface LeaseMapper {
    
    @Mappings({
        @Mapping(target = "instanceName", source = "leaseEntity.instance.instanceName")
    })
    LeaseView entityToView(LeaseEntity leaseEntity);
    
    @Mappings({
        @Mapping(target = "version", ignore = true)
       // @Mapping(source = "instanceName", target = "instance", qualifiedByName = "mapInstanceEntity")
    })
    LeaseEntity viewToEntity(LeaseView leaseView);

    List<LeaseView> entityListToViewList(List<LeaseEntity> leaseEntities);

    List<LeaseEntity> viewListToEntityList(List<LeaseView> leaseViews);

    // @Named(value ="mapInstanceEntity")
    // public static InstanceEntity mapInstanceEntity(String instanceName){
    //     return  instanceRepo.findByInstanceName(instanceName).block();
    // }
}
