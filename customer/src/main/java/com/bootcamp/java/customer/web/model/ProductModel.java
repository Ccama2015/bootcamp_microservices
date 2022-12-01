package com.bootcamp.java.customer.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
    @JsonIgnore
    private String id;

    @NotBlank(message="Account Number cannot be null or empty")
    private String accountNumber;

    private String cardNumber;

    @NotBlank(message="Balance cannot be null or empty")
    private String balance;

    private String signatory;

    @NotBlank(message="Product type cannot be null or empty")
    private String productType;

    @NotBlank(message="Identity Number Customer cannot be null or empty")
    private String identityNumberCustomer;

}