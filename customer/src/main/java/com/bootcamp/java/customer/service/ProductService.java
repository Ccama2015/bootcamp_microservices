package com.bootcamp.java.customer.service;

import com.bootcamp.java.customer.domain.Customer;
import com.bootcamp.java.customer.domain.Product;
import com.bootcamp.java.customer.repository.CustomerRepository;
import com.bootcamp.java.customer.repository.ProductRepository;
import com.bootcamp.java.customer.web.dto.CustomerTypeEnum;
import com.bootcamp.java.customer.web.dto.ProductTypeEnum;
import com.bootcamp.java.customer.web.mapper.IProductMapper;
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
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private IProductMapper productMapper;

    public Flux<Product> findAll(){
        log.debug("findAll executed");
        return productRepository.findAll();
    }

    public Mono<Product> findById(String productId){
        log.debug("findById executed {}", productId);
        return productRepository.findById(productId);
    }

    public Mono<Product> create(Product product){
        log.debug("create executed {}", product);

        if(product.getProductType().trim().equals(ProductTypeEnum.SAVING_ACCOUNT.getValue()) ||
                product.getProductType().trim().equals(ProductTypeEnum.CURRENT_ACCOUNT.getValue()) ||
                product.getProductType().trim().equals(ProductTypeEnum.FIXED_TERM_ACCOUNT.getValue())){
            /*if(customer.get){

            }*/
            Flux<Customer> customerFlux = customerRepository.findByIdentityNumber(product.getIdentityNumberCustomer())
                    .filter(dbCustomer -> dbCustomer.getType().equals(CustomerTypeEnum.PERSONNEL.getValue()))
                    .switchIfEmpty(b -> {
                    });


            Flux<Product> productFlux = productRepository.findByIdentityNumberCustomer(product.getIdentityNumberCustomer())
                    .filter(dbProduct -> dbProduct.getProductType().equals(ProductTypeEnum.SAVING_ACCOUNT.getValue()) ||
                            dbProduct.getProductType().equals(ProductTypeEnum.CURRENT_ACCOUNT.getValue()) ||
                            dbProduct.getProductType().equals(ProductTypeEnum.FIXED_TERM_ACCOUNT.getValue()))
                    .switchIfEmpty(p -> {
                        productRepository.save(product);
                    });


        }
        /*if (product.getType().trim().equals(ProductTypeEnum.BUSINESS.getValue()) ||
                product.getType().trim().equals(ProductTypeEnum.PERSONNEL.getValue())) {*/
        return productRepository.save(product);
        /*}
        else {
            return Mono.error(new InvalidProductTypeException());

        }*/
    }

    public Mono<Product> update(String productId, Product product){
        log.debug("update executed {}:{}", productId, product);
        return productRepository.findById(productId)
                .flatMap(dbProduct -> {
                    productMapper.update(dbProduct, product);
                    return productRepository.save(dbProduct);
                });
    }

    public Mono<Product> delete(String productId){
        log.debug("delete executed {}", productId);
        return productRepository.findById(productId)
                .flatMap(existingProduct -> productRepository.delete(existingProduct)
                        .then(Mono.just(existingProduct)));
    }

}