package com.bootcamp.java.customer.web.mapper;

import com.bootcamp.java.customer.domain.Customer;
import com.bootcamp.java.customer.web.model.CustomerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ICustomerMapper {
    Customer modelToEntity (CustomerModel model);
    CustomerModel entityToModel (Customer event);
    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Customer entity, Customer updateEntity);
}