package com.inventi.app.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.inventi.app.entity.BankStatement} entity
 */
@Data
public class BankStatementDto implements Serializable {
    private final String accountNumber;
    private final LocalDateTime operationDate;
    private final String beneficiary;
    private final String comment;
    private final Double amount;
    private final String currency;
}