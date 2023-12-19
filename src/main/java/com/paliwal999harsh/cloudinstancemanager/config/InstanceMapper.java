package com.paliwal999harsh.cloudinstancemanager.config;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.paliwal999harsh.cloudinstancemanager.model.InstanceEntity;
import com.paliwal999harsh.cloudinstancemanager.view.InstanceView;

@Mapper(componentModel = "spring")
public interface InstanceMapper {
    
    InstanceView entityToView(InstanceEntity instanceEntity);
    
    @Mappings({
        @Mapping(target = "version", ignore = true),
        @Mapping(target = "id", ignore = true)
    })
    InstanceEntity viewToEntity(InstanceView instanceView);

    List<InstanceView> entityListToViewList(List<InstanceEntity> instanceEntities);

    List<InstanceEntity> viewListToEntityList(List<InstanceView> instanceViews);
}
