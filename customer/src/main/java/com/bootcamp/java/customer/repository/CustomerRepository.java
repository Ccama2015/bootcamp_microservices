package com.bootcamp.java.customer.repository;

import com.bootcamp.java.customer.domain.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
    Mono<Customer> findByIdentityNumber(String identityNumber);
}

