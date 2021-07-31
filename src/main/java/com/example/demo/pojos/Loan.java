package com.example.demo.pojos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class Loan {

    @Getter @Setter private Integer id;
    @Getter @Setter private BigDecimal amount;
    @Getter @Setter private BigDecimal interestRate;
    @Getter @Setter private BigDecimal defaultLikelihood;
    @Getter @Setter private String state;

    public Loan(
            Integer id,
            BigDecimal amount,
            BigDecimal interestRate,
            BigDecimal defaultLikelihood,
            String state) {
        super();
        this.id = id;
        this.amount = amount;
        this.interestRate = interestRate;
        this.defaultLikelihood = defaultLikelihood;
        this.state = state;
    }

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
