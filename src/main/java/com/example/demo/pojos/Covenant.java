package com.example.demo.pojos;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class Covenant {
    @Getter @Setter @CsvBindByName(column="facility_id") private Integer facilityId;
    @Getter @Setter @CsvBindByName(column="max_default_likelihood") private BigDecimal maxDefaultLikelihood;
    @Getter @Setter @CsvBindByName(column="bank_id") private Integer bankId;
    @Getter @Setter @CsvBindByName(column="banned_state") private String bannedState;

    @Override
    public String toString() {
        return "Covenant [facilityId=" +facilityId+", maxDefaultLikelihood=" + maxDefaultLikelihood+ ", bannedState="+ bannedState+ "]";
    }
}
