package com.example.demo;

import com.example.demo.pojos.Loan;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoanService {
    @Getter private List<Loan> loanList;
    public LoanService(String csv){
        loanList = prepareLoansCsv(csv);
    }

    private List<Loan> prepareLoansCsv(String csv){
        List<Loan> loans = new ArrayList<>();
        try {
            loans = new CsvToBeanBuilder(new FileReader(csv))
                    .withType(Loan.class)
                    .build()
                    .parse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        loans.forEach(System.out::println);
        return loans;
    }
    public void sourceLoan(Loan loan){

    }
}
