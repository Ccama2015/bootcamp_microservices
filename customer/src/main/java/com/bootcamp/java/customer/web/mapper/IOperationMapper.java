package com.bootcamp.java.customer.web.mapper;

import com.bootcamp.java.customer.domain.Operation;
import com.bootcamp.java.customer.web.model.OperationModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IOperationMapper {
    Operation modelToEntity (OperationModel model);
    OperationModel entityToModel (Operation event);
    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Operation entity, Operation updateEntity);
}