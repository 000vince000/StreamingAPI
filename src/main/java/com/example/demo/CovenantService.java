package com.example.demo;

import com.example.demo.pojos.Covenant;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CovenantService {
    @Getter private List<Covenant> covenantList;
    public CovenantService(String csv){
        covenantList = prepareCovenantsCsv(csv);
    }

    private List<Covenant> prepareCovenantsCsv(String csv){
        List<Covenant> covenants = new ArrayList<>();
        try {
            covenants = new CsvToBeanBuilder(new FileReader(csv))
                    .withType(Covenant.class)
                    .build()
                    .parse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        covenants.forEach(System.out::println);
        return covenants;
    }
}
