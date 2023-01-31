package com.inventi.app.service;

import com.inventi.app.csv.BankStatementCsvBean;
import com.inventi.app.entity.BankStatement;
import com.inventi.app.mapper.BankStatementMapper;
import com.inventi.app.repository.BankStatementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankStatementServiceTest {

    @InjectMocks
    private BankStatementService bankStatementService;

    @Mock
    private CsvService csvService;
    @Mock
    private BankStatementRepository bankStatementRepository;

    private final BankStatementMapper mapper = new BankStatementMapper();

    @Test
    void when_accountHasSentBiggerAmountThanReceived_then_ShouldReturnNegativeAccountBalance() {
        //set up
        List<BankStatement> bankStatements = List.of(
                new BankStatement(1L, "account1",
                        LocalDateTime.of(2022, 9, 19, 20, 55, 41),
                        "account2", "comment", 1100D, "EUR"),
                new BankStatement(2L, "account1",
                        LocalDateTime.of(2022, 10, 19, 20, 55, 41),
                        "account2", "comment", 1100D, "EUR")
        );

        when(bankStatementRepository.findAllByOperationDateIsBetweenAndAccountNumber(LocalDateTime.of(2022, 8, 19, 0, 0, 0), LocalDateTime.of(2022, 9, 19, 23, 59, 59), "account1"))
                .thenReturn(bankStatements);
        when(bankStatementRepository.findAllByOperationDateIsBetweenAndBeneficiary(LocalDateTime.of(2022, 8, 19, 0, 0, 0), LocalDateTime.of(2022, 9, 19, 23, 59, 59), "account1"))
                .thenReturn(bankStatements);
        //execute
        double accountBalance = bankStatementService.accountBalance("account1", LocalDate.of(2022, 8, 19), LocalDate.of(2022, 9, 19));

        //verify
        Assertions.assertEquals(-2200, accountBalance);

    }

    @Test
    void when_accountHasReceivedBiggerAmountThanSent_then_ShouldReturnPositiveAccountBalance() {
        //set up
        List<BankStatement> bankStatements = List.of(
                new BankStatement(1L, "account2",
                        LocalDateTime.of(2022, 9, 19, 20, 55, 41),
                        "account1", "comment", 1100D, "EUR"),
                new BankStatement(2L, "account2",
                        LocalDateTime.of(2022, 9, 19, 20, 55, 41),
                        "account1", "comment", 1100D, "EUR")
        );

        when(bankStatementRepository.findAllByOperationDateIsBetweenAndAccountNumber(LocalDateTime.of(2022, 8, 19, 0, 0, 0), LocalDateTime.of(2022, 9, 19, 23, 59, 59), "account1"))
                .thenReturn(bankStatements);
        when(bankStatementRepository.findAllByOperationDateIsBetweenAndBeneficiary(LocalDateTime.of(2022, 8, 19, 0, 0, 0), LocalDateTime.of(2022, 9, 19, 23, 59, 59), "account1"))
                .thenReturn(bankStatements);
        //execute
        double accountBalance = bankStatementService.accountBalance("account1", LocalDate.of(2022, 8, 19), LocalDate.of(2022, 9, 19));

        //verify
        Assertions.assertEquals(2200, accountBalance);

    }

    @Test
    void when_accountHasSentAndReceivedSameAmount_then_ShouldReturnAccountBalanceAsZero() {
        List<BankStatement> bankStatements = List.of(
                new BankStatement(1L, "account1",
                        LocalDateTime.of(2022, 9, 19, 20, 55, 41),
                        "account2", "comment", 1100D, "EUR"),
                new BankStatement(2L, "account2",
                        LocalDateTime.of(2022, 9, 19, 20, 55, 41),
                        "account1", "comment", 1100D, "EUR")
        );

        when(bankStatementRepository.findAllByOperationDateIsBetweenAndAccountNumber(LocalDateTime.of(2022, 8, 19, 0, 0, 0), LocalDateTime.of(2022, 9, 19, 23, 59, 59), "account1"))
                .thenReturn(bankStatements);
        when(bankStatementRepository.findAllByOperationDateIsBetweenAndBeneficiary(LocalDateTime.of(2022, 8, 19, 0, 0, 0), LocalDateTime.of(2022, 9, 19, 23, 59, 59), "account1"))
                .thenReturn(bankStatements);

        double accountBalance = bankStatementService.accountBalance("account1", LocalDate.of(2022, 8, 19), LocalDate.of(2022, 9, 19));

        Assertions.assertEquals(0, accountBalance);

    }

    @Test
    void when_accountNumberAndBeneficiaryIsNull_then_ShouldCountAccountBalanceAsZero() {
        List<BankStatement> bankStatements = List.of(
                new BankStatement(1L, "account2",
                        LocalDateTime.of(2022, 9, 19, 20, 55, 41),
                        "account3", "comment", 1100D, "EUR"),
                new BankStatement(2L, "account2",
                        LocalDateTime.of(2022, 9, 19, 20, 55, 41),
                        "account3", "comment", 1100D, "EUR")
        );


        when(bankStatementRepository.findAllByOperationDateIsBetweenAndAccountNumber(LocalDateTime.of(2022, 8, 19, 0, 0, 0), LocalDateTime.of(2022, 9, 19, 23, 59, 59), "account1"))
                .thenReturn(bankStatements);
        when(bankStatementRepository.findAllByOperationDateIsBetweenAndBeneficiary(LocalDateTime.of(2022, 8, 19, 0, 0, 0), LocalDateTime.of(2022, 9, 19, 23, 59, 59), "account1"))
                .thenReturn(bankStatements);

        double accountBalance = bankStatementService.accountBalance("account1", LocalDate.of(2022, 8, 19), LocalDate.of(2022, 9, 19));

        Assertions.assertEquals(0, accountBalance);

    }

    @Test
    void when_accountNumberIsNull_then_ShouldCountAccountBalanceForBeneficiary() {
        List<BankStatement> bankStatementsByBeneficiary = List.of(
                new BankStatement(1L, "account2",
                        LocalDateTime.of(2022, 2, 19, 20, 55, 41),
                        "account1", "comment", 1100D, "EUR"),
                new BankStatement(2L, "account2",
                        LocalDateTime.of(2022, 3, 19, 20, 55, 41),
                        "account1", "comment", 1100D, "EUR")
        );

        List<BankStatement> bankStatementByAccountNumber = new ArrayList<>();

        when(bankStatementRepository.findAllByOperationDateIsBetweenAndAccountNumber(LocalDateTime.of(2022, 2, 18, 0, 0, 0), LocalDateTime.of(2022, 3, 19, 23, 59, 59), "account1"))
                .thenReturn(bankStatementByAccountNumber);
        when(bankStatementRepository.findAllByOperationDateIsBetweenAndBeneficiary(LocalDateTime.of(2022, 2, 18, 0, 0, 0), LocalDateTime.of(2022, 3, 19, 23, 59, 59), "account1"))
                .thenReturn(bankStatementsByBeneficiary);

        double accountBalance = bankStatementService.accountBalance("account1", LocalDate.of(2022, 2, 18), LocalDate.of(2022, 3, 19));

        Assertions.assertEquals(2200, accountBalance);
    }

    @Test
    void when_beneficiaryIsNull_then_ShouldCountAccountBalanceForAccountNumber() {
        List<BankStatement> bankStatementByAccountNumber = List.of(
                new BankStatement(1L, "account1",
                        LocalDateTime.of(2022, 2, 19, 20, 55, 41),
                        "account2", "comment", 1100D, "EUR"),
                new BankStatement(2L, "account1",
                        LocalDateTime.of(2022, 3, 19, 20, 55, 41),
                        "account2", "comment", 1100D, "EUR")
        );

        List<BankStatement> bankStatementsByBeneficiary = new ArrayList<>();


        when(bankStatementRepository.findAllByOperationDateIsBetweenAndAccountNumber(LocalDateTime.of(2022, 2, 18, 0, 0, 0), LocalDateTime.of(2022, 3, 19, 23, 59, 59), "account1"))
                .thenReturn(bankStatementByAccountNumber);
        when(bankStatementRepository.findAllByOperationDateIsBetweenAndBeneficiary(LocalDateTime.of(2022, 2, 18, 0, 0, 0), LocalDateTime.of(2022, 3, 19, 23, 59, 59), "account1"))
                .thenReturn(bankStatementsByBeneficiary);

        double accountBalance = bankStatementService.accountBalance("account1", LocalDate.of(2022, 2, 18), LocalDate.of(2022, 3, 19));

        Assertions.assertEquals(-2200, accountBalance);

    }

    @Test
    void when_csvFileIsTransformedToCsvBeans_then_ShouldSaveCsvBeansToRepository() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        List<BankStatementCsvBean> beans = new ArrayList<>();
        beans.add(new BankStatementCsvBean(1L, "account1",
                LocalDateTime.of(2022, 9, 19, 20, 55, 41),
                "account2", "comment", 500, "EUR"));
        beans.add(new BankStatementCsvBean(2L, "account2",
                LocalDateTime.of(2022, 10, 14, 12, 42, 42),
                "account1", "comment", 1000, "EUR"));
        beans.add(new BankStatementCsvBean(3L, "account3",
                LocalDateTime.of(2022, 4, 30, 4, 55, 51),
                "account2", "comment", 50, "EUR"));

        List<BankStatement> bankStatements = beans.stream()
                .map(mapper::mapBankStatement).toList();
        byte[] data = new byte[]{1, 2, 3, 4};

        when(file.getBytes()).thenReturn(data);
        when(csvService.readFromCsvToBean(data)).thenReturn(beans);

        bankStatementService.saveFile(file);

        verify(bankStatementRepository).saveAll(bankStatements);
    }

    @Test
    void when_allCsvBeansByOperationDateMappedToList_then_writeToFileFromCsvBean() throws Exception {
        Writer writer = new FileWriter("BankStatement.csv");
        List<BankStatement> bankStatements = List.of(
                new BankStatement(1L, "account1",
                        LocalDateTime.of(2022, 9, 19, 20, 55, 41),
                        "account2", "comment", 1100D, "EUR"),
                new BankStatement(2L, "account1",
                        LocalDateTime.of(2022, 10, 19, 20, 55, 41),
                        "account2", "comment", 1100D, "EUR")
        );

        when(bankStatementRepository.findAllByOperationDateIsBetween(
                LocalDateTime.of(2022, 9, 10, 0, 0, 0),
                LocalDateTime.of(2022, 10, 20, 23, 59, 59)))
                .thenReturn(bankStatements);

        List<BankStatementCsvBean> bankStatementCsvBeans = bankStatements.stream()
                .map(mapper::mapBankStatementCsvBean).toList();

        bankStatementService.exportFile(writer, LocalDate.of(2022, 9, 10), LocalDate.of(2022, 10, 20));

        verify(csvService).writeToCsvFromBean(bankStatementCsvBeans, writer);

    }
}