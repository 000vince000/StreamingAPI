package com.example.demo.pojos;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class Loan {
    @Getter @Setter @CsvBindByName(column="interest_rate") private BigDecimal interestRate;
    @Getter @Setter @CsvBindByName(column="amount") private BigDecimal amount;
    @Getter @Setter @CsvBindByName(column="id") private Integer id;
    @Getter @Setter @CsvBindByName(column="default_likelihood") private BigDecimal defaultLikelihood;
    @Getter @Setter @CsvBindByName(column="state") private String state;


    //TODO
    @Override
    public String toString() {
        return "Loan [id="
                + id
                + ", amount="
                + amount
                + ", interestRate="
                + interestRate
                + ", defaultLikelihood="
                + defaultLikelihood
                + ", state="
                + state
                + "]";
    }
}
