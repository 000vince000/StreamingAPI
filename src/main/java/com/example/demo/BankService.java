package com.example.demo;

import com.example.demo.pojos.Bank;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class BankService {
    @Getter private List<Bank> bankList;
    public BankService(String csv){
        bankList = prepareBanksCsv(csv);
    }

    private List<Bank> prepareBanksCsv(String csv){
        List<Bank> banks = new ArrayList<>();
        try {
            banks = new CsvToBeanBuilder(new FileReader(csv))
                    .withType(Bank.class)
                    .build()
                    .parse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return banks;
    }
}
