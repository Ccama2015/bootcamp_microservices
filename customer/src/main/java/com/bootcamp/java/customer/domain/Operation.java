package com.bootcamp.java.customer.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"accountNumber"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "operation")
public class Operation {
    @Id
    private String id;
    @NotNull
    private String accountNumber;
    @NotNull
    private String operationType;
    @NotNull
    private String balance;
    @NotNull
    private LocalDate date;
    @NotNull
    private String identityNumberCustomer;

}
