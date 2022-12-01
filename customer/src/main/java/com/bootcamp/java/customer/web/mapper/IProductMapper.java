package com.bootcamp.java.customer.web.mapper;

import com.bootcamp.java.customer.domain.Product;
import com.bootcamp.java.customer.web.model.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IProductMapper {
    Product modelToEntity (ProductModel model);
    ProductModel entityToModel (Product event);
    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Product entity, Product updateEntity);
}