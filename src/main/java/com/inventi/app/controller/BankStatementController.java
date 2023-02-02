package com.inventi.app.controller;

import com.inventi.app.dto.BankStatementRequestDto;
import com.inventi.app.dto.BankStatementResponseDto;
import com.inventi.app.service.BankStatementService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@AllArgsConstructor
@RestController
public class BankStatementController {
    private BankStatementService bankStatementService;

    @GetMapping("/balance")
    ResponseEntity<BankStatementResponseDto> accountBalance(@Valid @RequestBody BankStatementRequestDto requestDto) {

        double accountBalance = bankStatementService.accountBalance(requestDto.getAccountNumber(), requestDto.getOperationDateFrom(), requestDto.getOperationDateTo());
        BankStatementResponseDto responseDto = new BankStatementResponseDto(accountBalance);

        return new ResponseEntity<>(
                responseDto,
                HttpStatus.OK
        );
    }

    @GetMapping("/download")
    public void getFile(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from, LocalDate to,
                        HttpServletResponse response) throws Exception {

        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"BankStatement.csv\"");

        bankStatementService.exportFile(response.getWriter(), from, to);
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            bankStatementService.saveFile(file);
            String message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception exception) {
            String message = "Could not upload the file: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}
