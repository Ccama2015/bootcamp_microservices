package com.bootcamp.java.customer.web;

import com.bootcamp.java.customer.domain.Customer;
import com.bootcamp.java.customer.service.CustomerService;
import com.bootcamp.java.customer.web.mapper.ICustomerMapper;
import com.bootcamp.java.customer.web.model.CustomerModel;
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
@RequestMapping("/v1/customer")
public class CustomerController {
    @Value("${spring.application.name}")
    String name;
    @Value("${server.port}")
    String port;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ICustomerMapper customerMapper;
    @GetMapping
    public Mono<ResponseEntity<Flux<CustomerModel>>> getAll(){
        log.info("getAll executed");

        return Mono.just(ResponseEntity.ok()
                .body(customerService.findAll()
                        .map(customer -> customerMapper.entityToModel(customer))));
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<CustomerModel>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        Mono<Customer> response = customerService.findById(id);
        return response
                .map(customer -> customerMapper.entityToModel(customer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<CustomerModel>> create(@Valid @RequestBody CustomerModel
                                                              request){
        log.info("create executed {}", request);
        return customerService.create(customerMapper.modelToEntity(request))
                .map(customer -> customerMapper.entityToModel(customer))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "customer", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CustomerModel>> updateById(@PathVariable String id, @Valid
    @RequestBody CustomerModel request){
        log.info("updateById executed {}:{}", id, request);
        return customerService.update(id, customerMapper.modelToEntity(request))
                .map(customer -> customerMapper.entityToModel(customer))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "customer", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        log.info("deleteById executed {}", id);
        return customerService.delete(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
