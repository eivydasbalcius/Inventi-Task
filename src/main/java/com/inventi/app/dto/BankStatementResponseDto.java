package com.inventi.app.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.inventi.app.entity.BankStatement} entity
 */
@Data
public class BankStatementResponseDto implements Serializable {

    private final Double amount;
}