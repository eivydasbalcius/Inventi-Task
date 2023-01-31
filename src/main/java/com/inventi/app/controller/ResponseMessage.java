package com.inventi.app.controller;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.inventi.app.entity.BankStatement} entity
 */
@Data
public class ResponseMessage implements Serializable {
    private final String message;
}