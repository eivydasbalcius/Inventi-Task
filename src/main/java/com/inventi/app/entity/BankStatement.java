package com.inventi.app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_statement")
public class BankStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "account_number", nullable = false)
    private String accountNumber;
    @Column(name = "operation_date", nullable = false)
    private LocalDateTime operationDate;
    @Column(name = "beneficiary", nullable = false)
    private String beneficiary;
    @Column(name = "comment")
    private String comment;
    @Column(name = "ammount", nullable = false)
    private Double amount;
    @Column(name = "currency", nullable = false)
    private String currency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BankStatement that = (BankStatement) o;

        return new EqualsBuilder().append(id, that.id).append(accountNumber, that.accountNumber).append(operationDate, that.operationDate).append(beneficiary, that.beneficiary).append(comment, that.comment).append(amount, that.amount).append(currency, that.currency).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(accountNumber).append(operationDate).append(beneficiary).append(comment).append(amount).append(currency).toHashCode();
    }

}