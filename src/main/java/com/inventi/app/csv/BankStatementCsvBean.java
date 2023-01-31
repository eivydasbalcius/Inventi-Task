package com.inventi.app.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankStatementCsvBean implements Serializable {

    @CsvBindByName
    private Long id;
    @CsvBindByName(required = true)
    private String accountNumber;
    @CsvBindByName(required = true)
    @CsvDate("yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime operationDate;
    @CsvBindByName(required = true)
    private String beneficiary;
    @CsvBindByName
    private String comment;
    @CsvBindByName(required = true)
    private double amount;
    @CsvBindByName(required = true)
    private String currency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BankStatementCsvBean that = (BankStatementCsvBean) o;

        return new EqualsBuilder().append(amount, that.amount).append(id, that.id).append(accountNumber, that.accountNumber).append(operationDate, that.operationDate).append(beneficiary, that.beneficiary).append(comment, that.comment).append(currency, that.currency).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(accountNumber).append(operationDate).append(beneficiary).append(comment).append(amount).append(currency).toHashCode();
    }
}
