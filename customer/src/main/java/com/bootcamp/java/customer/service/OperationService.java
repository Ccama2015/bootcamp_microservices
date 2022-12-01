package com.bootcamp.java.customer.service;

import com.bootcamp.java.customer.domain.Operation;
import com.bootcamp.java.customer.repository.OperationRepository;

import com.bootcamp.java.customer.web.mapper.IOperationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private IOperationMapper operationMapper;

    public Flux<Operation> findAll(){
        log.debug("findAll executed");
        return operationRepository.findAll();
    }

    public Mono<Operation> findById(String operationId){
        log.debug("findById executed {}", operationId);
        return operationRepository.findById(operationId);
    }

    public Mono<Operation> create(Operation operation){
        log.debug("create executed {}", operation);

        /*if (operation.getType().trim().equals(OperationTypeEnum.BUSINESS.getValue()) ||
                operation.getType().trim().equals(OperationTypeEnum.PERSONNEL.getValue())) {*/
            return operationRepository.save(operation);
       /* }
        else {
            return Mono.error(new InvalidOperationTypeException());

        }*/
    }

    public Mono<Operation> update(String operationId, Operation operation){
        log.debug("update executed {}:{}", operationId, operation);
        return operationRepository.findById(operationId)
                .flatMap(dbOperation -> {
                    operationMapper.update(dbOperation, operation);
                    return operationRepository.save(dbOperation);
                });
    }

    public Mono<Operation> delete(String operationId){
        log.debug("delete executed {}", operationId);
        return operationRepository.findById(operationId)
                .flatMap(existingOperation -> operationRepository.delete(existingOperation)
                        .then(Mono.just(existingOperation)));
    }

}