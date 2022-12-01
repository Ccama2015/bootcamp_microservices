package com.bootcamp.java.customer.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class OperationModel {
    @JsonIgnore
    private String id;

    @NotBlank(message="Account number cannot be null or empty")
    private String accountNumber;

    @NotBlank(message="Operation type be null or empty")
    private String operationType;

    @NotBlank(message="Balance cannot be null or empty")
    private String balance;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotBlank(message="Identity Number Customer cannot be null or empty")
    private String identityNumberCustomer;

}