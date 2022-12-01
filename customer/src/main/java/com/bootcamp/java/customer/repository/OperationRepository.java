package com.bootcamp.java.customer.repository;

import com.bootcamp.java.customer.domain.Operation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OperationRepository extends ReactiveMongoRepository<Operation, String> {
    Flux<Operation> findByAccountNumber(String accountNumber);
}

