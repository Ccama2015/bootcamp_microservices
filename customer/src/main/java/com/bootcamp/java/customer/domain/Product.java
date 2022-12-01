package com.bootcamp.java.customer.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"accountNumber"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "product")
public class Product {
    @Id
    private String id;
    @NotNull
    private String accountNumber;
    private String cardNumber;
    @NotNull
    private String balance;
    private String signatory;
    @NotNull
    private String productType;
    @NotNull
    private String identityNumberCustomer;

}
