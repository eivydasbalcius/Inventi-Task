package com.inventi.app.mapper;

import com.inventi.app.csv.BankStatementCsvBean;
import com.inventi.app.entity.BankStatement;

public class BankStatementMapper {

    public BankStatement mapBankStatement(BankStatementCsvBean bean) {
        return BankStatement.builder()
                .id(bean.getId())
                .accountNumber(bean.getAccountNumber())
                .operationDate(bean.getOperationDate())
                .beneficiary(bean.getBeneficiary())
                .comment(bean.getComment())
                .amount(bean.getAmount())
                .currency(bean.getCurrency())
                .build();
    }

    public BankStatementCsvBean mapBankStatementCsvBean(BankStatement bean) {
        return BankStatementCsvBean.builder()
                .id(bean.getId())
                .accountNumber(bean.getAccountNumber())
                .operationDate(bean.getOperationDate())
                .beneficiary(bean.getBeneficiary())
                .comment(bean.getComment())
                .amount(bean.getAmount())
                .currency(bean.getCurrency())
                .build();
    }
}
