package com.bootcamp.java.customer.repository;

import com.bootcamp.java.customer.domain.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Flux<Product> findByAccountNumber(String accountNumber);
    Flux<Product> findByIdentityNumberCustomer(String identityNumberCustomer);
}

