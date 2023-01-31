package com.inventi.app.service;

import com.inventi.app.csv.BankStatementCsvBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CsvServiceTest {

    @InjectMocks
    private CsvService csvService;

    @Test
    void when_csvFileContainsStatements_then_ShouldReturnListOfStatements() throws Exception {
        Resource resource = getResource("csv/BankStatement.csv");
        List<BankStatementCsvBean> bankStatementCsvBeans = csvService.readFromCsvToBean(resource.getInputStream().readAllBytes());
        Assertions.assertEquals(3, bankStatementCsvBeans.size());
    }

    @Test
    void when_csvFileIsEmpty_then_shouldReturnEmptyList() throws Exception {
        Resource resource = getResource("csv/BankStatementEmpty.csv");
        List<BankStatementCsvBean> bankStatementCsvBeans = csvService.readFromCsvToBean(resource.getInputStream().readAllBytes());
        Assertions.assertTrue(bankStatementCsvBeans.isEmpty());
    }

    @Test
    void when_fileWithBankStatementsCreated_then_ShouldWriteValuesToTheFile() throws Exception {
        Writer writer = new FileWriter("BankStatement.csv");
        List<BankStatementCsvBean> beans = new ArrayList<>();

        beans.add(new BankStatementCsvBean(1L, "account1", LocalDateTime.of(2022, 9, 19, 20, 55, 41), "account2", "comment", 500, "EUR"));
        beans.add(new BankStatementCsvBean(2L, "account2", LocalDateTime.of(2022, 10, 14, 12, 42, 42), "account1", "comment", 1000, "EUR"));
        beans.add(new BankStatementCsvBean(3L, "account3", LocalDateTime.of(2022, 4, 30, 4, 55, 51), "account2", "comment", 50, "EUR"));
        csvService.writeToCsvFromBean(beans, writer);

        List<String> bankStatementsCsv = Files.readAllLines(Path.of("BankStatement.csv"));

        Assertions.assertEquals(4, bankStatementsCsv.size());
        Assertions.assertTrue(bankStatementsCsv.get(1).contains("500"));
    }

    private Resource getResource(String fileName) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource("classpath:" + fileName);
    }
}