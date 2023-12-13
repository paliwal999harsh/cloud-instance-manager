package com.paliwal999harsh.cloudinstancemanager.config;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.paliwal999harsh.cloudinstancemanager.model.LeaseEntity;
import com.paliwal999harsh.cloudinstancemanager.view.LeaseView;

import reactor.core.publisher.Flux;

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

    default List<LeaseView> entityListToViewList(Flux<LeaseEntity> leaseEntities) {
        return leaseEntities
                .map(this::entityToView)
                .collectList()
                .block();
    }

    default List<LeaseEntity> viewListToEntityList(Flux<LeaseView> leaseViews) {
        return leaseViews
                .map(this::viewToEntity)
                .collectList()
                .block();
    }

    // @Named(value ="mapInstanceEntity")
    // public static InstanceEntity mapInstanceEntity(String instanceName){
    //     return  instanceRepo.findByInstanceName(instanceName).block();
    // }
}
