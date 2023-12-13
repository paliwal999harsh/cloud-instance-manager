package com.paliwal999harsh.cloudinstancemanager.config;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.paliwal999harsh.cloudinstancemanager.model.InstanceEntity;
import com.paliwal999harsh.cloudinstancemanager.view.InstanceView;

import reactor.core.publisher.Flux;

@Mapper(componentModel = "spring")
public interface InstanceMapper {
    
    InstanceView entityToView(InstanceEntity instanceEntity);
    
    @Mappings({
        @Mapping(target = "version", ignore = true)
    })
    InstanceEntity viewToEntity(InstanceView instanceView);

    default List<InstanceView> entityListToViewList(Lis<InstanceEntity> instanceEntities) {
        return instanceEntities
                .map(this::entityToView)
                .collectList()
                .block();
    }

    default List<InstanceEntity> viewListToEntityList(Flux<InstanceView> instanceViews) {
        return instanceViews
                .map(this::viewToEntity)
                .collectList()
                .block();
    }
}
