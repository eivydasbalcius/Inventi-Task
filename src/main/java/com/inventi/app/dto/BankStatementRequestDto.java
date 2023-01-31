package com.inventi.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * A DTO for the {@link com.inventi.app.entity.BankStatement} entity
 */
@Data
public class BankStatementRequestDto implements Serializable {

    @NotBlank
    private final String accountNumber;

    private final LocalDate operationDateFrom;

    private final LocalDate operationDateTo;

}
