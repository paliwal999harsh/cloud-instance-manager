package com.paliwal999harsh.cloudinstancemanager.microservices.lease.config;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.paliwal999harsh.cloudinstancemanager.dto.LeaseDTO;
import com.paliwal999harsh.cloudinstancemanager.microservices.lease.model.LeaseEntity;

@Mapper(componentModel = "spring")
public interface LeaseMapper {
    
    LeaseDTO entityToDto(LeaseEntity leaseEntity);
    
    @Mappings({
        @Mapping(target = "version", ignore = true),
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "sysCreatedOn", ignore = true),
        @Mapping(target = "sysUpdatedOn", ignore = true)
    })
    LeaseEntity viewToEntity(LeaseDTO leaseDto);

    List<LeaseDTO> entityListToDtoList(List<LeaseEntity> leaseEntities);

    List<LeaseEntity> viewListToEntityList(List<LeaseDTO> leaseDtos);
}
