package com.inventi.app.repository;

import com.inventi.app.entity.BankStatement;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BankStatementRepository extends CrudRepository<BankStatement, Long> {
    List<BankStatement> findAllByOperationDateIsBetweenAndBeneficiary(LocalDateTime from, LocalDateTime to, String beneficiary);

    List<BankStatement> findAllByOperationDateIsBetweenAndAccountNumber(LocalDateTime from, LocalDateTime to, String accountNumber);

    List<BankStatement> findAllByOperationDateIsBetween(LocalDateTime from, LocalDateTime to);

}
