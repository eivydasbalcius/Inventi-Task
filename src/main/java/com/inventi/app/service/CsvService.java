package com.inventi.app.service;

import com.inventi.app.csv.BankStatementCsvBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;


@AllArgsConstructor
@Service
public class CsvService {

    public List<BankStatementCsvBean> readFromCsvToBean(byte[] file) {
        return new CsvToBeanBuilder<BankStatementCsvBean>(new CSVReader(new InputStreamReader(new ByteArrayInputStream(file), StandardCharsets.UTF_8)))
                .withType(BankStatementCsvBean.class)
                .withIgnoreEmptyLine(true)
                .build()
                .parse();
    }

    public void writeToCsvFromBean(List<BankStatementCsvBean> beans, Writer writer) throws Exception {
        StatefulBeanToCsv<BankStatementCsvBean> beanToCsv = new StatefulBeanToCsvBuilder<BankStatementCsvBean>(writer).build();
        beanToCsv.write(beans);
        writer.close();
    }
}
