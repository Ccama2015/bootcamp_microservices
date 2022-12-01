package com.bootcamp.java.customer.web.controller;

import com.bootcamp.java.customer.domain.Product;
import com.bootcamp.java.customer.service.ProductService;
import com.bootcamp.java.customer.web.mapper.IProductMapper;
import com.bootcamp.java.customer.web.model.ProductModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/product")
public class ProductController {
    @Value("${spring.application.name}")
    String name;
    @Value("${server.port}")
    String port;

    @Autowired
    private ProductService productService;
    @Autowired
    private IProductMapper productMapper;
    @GetMapping
    public Mono<ResponseEntity<Flux<ProductModel>>> getAll(){
        log.info("getAll executed");

        return Mono.just(ResponseEntity.ok()
                .body(productService.findAll()
                        .map(product -> productMapper.entityToModel(product))));
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductModel>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        Mono<Product> response = productService.findById(id);
        return response
                .map(product -> productMapper.entityToModel(product))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ProductModel>> create(@Valid @RequestBody ProductModel
                                                              request){
        log.info("create executed {}", request);
        return productService.create(productMapper.modelToEntity(request))
                .map(product -> productMapper.entityToModel(product))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "product", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductModel>> updateById(@PathVariable String id, @Valid
    @RequestBody ProductModel request){
        log.info("updateById executed {}:{}", id, request);
        return productService.update(id, productMapper.modelToEntity(request))
                .map(product -> productMapper.entityToModel(product))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "product", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        log.info("deleteById executed {}", id);
        return productService.delete(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
