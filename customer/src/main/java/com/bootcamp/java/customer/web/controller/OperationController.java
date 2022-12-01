package com.bootcamp.java.customer.web.controller;

import com.bootcamp.java.customer.domain.Operation;
import com.bootcamp.java.customer.service.OperationService;
import com.bootcamp.java.customer.web.mapper.IOperationMapper;
import com.bootcamp.java.customer.web.model.OperationModel;
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
@RequestMapping("/v1/operation")
public class OperationController {
    @Value("${spring.application.name}")
    String name;
    @Value("${server.port}")
    String port;

    @Autowired
    private OperationService operationService;
    @Autowired
    private IOperationMapper operationMapper;
    @GetMapping
    public Mono<ResponseEntity<Flux<OperationModel>>> getAll(){
        log.info("getAll executed");

        return Mono.just(ResponseEntity.ok()
                .body(operationService.findAll()
                        .map(operation -> operationMapper.entityToModel(operation))));
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<OperationModel>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        Mono<Operation> response = operationService.findById(id);
        return response
                .map(operation -> operationMapper.entityToModel(operation))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<OperationModel>> create(@Valid @RequestBody OperationModel
                                                              request){
        log.info("create executed {}", request);
        return operationService.create(operationMapper.modelToEntity(request))
                .map(operation -> operationMapper.entityToModel(operation))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "operation", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<OperationModel>> updateById(@PathVariable String id, @Valid
    @RequestBody OperationModel request){
        log.info("updateById executed {}:{}", id, request);
        return operationService.update(id, operationMapper.modelToEntity(request))
                .map(operation -> operationMapper.entityToModel(operation))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "operation", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        log.info("deleteById executed {}", id);
        return operationService.delete(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
