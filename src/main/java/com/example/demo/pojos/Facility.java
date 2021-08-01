package com.example.demo.pojos;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class Facility {
    @Getter @Setter @CsvBindByName(column="id") private Integer id;
    @Getter @Setter @CsvBindByName(column="bank_id") private Integer bankId;
    @Getter @Setter @CsvBindByName(column="interest_rate") private BigDecimal interestRate;
    @Getter @Setter @CsvBindByName(column="amount") private BigDecimal amount;

    @Override
    public String toString() {
        return "Facility [id="+ id+ ", bankId="+ bankId+ ", interestRate="+ interestRate+ ", amount="+ amount+ "]";
    }
}
