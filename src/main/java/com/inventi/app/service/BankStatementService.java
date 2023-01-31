package com.inventi.app.service;

import com.inventi.app.csv.BankStatementCsvBean;
import com.inventi.app.entity.BankStatement;
import com.inventi.app.mapper.BankStatementMapper;
import com.inventi.app.formatter.DateFormatter;
import com.inventi.app.repository.BankStatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class BankStatementService {

    private final BankStatementRepository repository;

    private final CsvService csvService;

    private final BankStatementMapper mapper = new BankStatementMapper();

    private final DateFormatter dateFormatter = new DateFormatter();

    public double accountBalance(String accountNumber, LocalDate from, LocalDate to) {

        List<BankStatement> bankStatementsByAccountNumber = repository.findAllByOperationDateIsBetweenAndAccountNumber(dateFormatter.getFrom(from), dateFormatter.getTo(to), accountNumber);

        List<BankStatement> bankStatementsByBeneficiary = repository.findAllByOperationDateIsBetweenAndBeneficiary(dateFormatter.getFrom(from), dateFormatter.getTo(to), accountNumber);

        if (bankStatementsByBeneficiary.isEmpty() && !bankStatementsByAccountNumber.isEmpty()) {
            return 0 - getBalanceByAccountNumber(accountNumber, bankStatementsByAccountNumber);

        } else if (!bankStatementsByBeneficiary.isEmpty() && bankStatementsByAccountNumber.isEmpty()) {
            return getBalanceByBeneficiary(accountNumber, bankStatementsByBeneficiary);

        }

        return getBalanceByBeneficiary(accountNumber, bankStatementsByBeneficiary) - getBalanceByAccountNumber(accountNumber, bankStatementsByAccountNumber);
    }

    private static double getBalanceByAccountNumber(String accountNumber, List<BankStatement> bankStatements) {
        return bankStatements
                .stream()
                .filter(bankStatement -> bankStatement.getAccountNumber().equals(accountNumber))
                .mapToDouble(BankStatement::getAmount).sum();
    }

    private static double getBalanceByBeneficiary(String accountNumber, List<BankStatement> bankStatements) {
        return bankStatements
                .stream()
                .filter(bankStatement -> bankStatement.getBeneficiary().equals(accountNumber))
                .mapToDouble(BankStatement::getAmount).sum();
    }

    public void saveFile(MultipartFile file) throws IOException {

        List<BankStatementCsvBean> csvBeans = csvService.readFromCsvToBean(file.getBytes());
        List<BankStatement> bankStatements = csvBeans.stream()
                .map(mapper::mapBankStatement).toList();

        repository.saveAll(bankStatements);
    }

    public void exportFile(Writer writer, LocalDate from, LocalDate to) throws Exception {

        List<BankStatement> bankStatementsByDate = repository.findAllByOperationDateIsBetween(dateFormatter.getFrom(from), dateFormatter.getTo(to));
        List<BankStatementCsvBean> bankStatementCsvBeans = bankStatementsByDate.stream()
                .map(mapper::mapBankStatementCsvBean).toList();

        csvService.writeToCsvFromBean(bankStatementCsvBeans, writer);
    }
}
