package com.example.demo;

import com.example.demo.pojos.Facility;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FacilityService {
    @Getter private List<Facility> facilityList;
    public FacilityService(String csv){
        facilityList = prepareFacilitiesCsv(csv);
    }
    private List<Facility> prepareFacilitiesCsv(String csv){
        List<Facility> facilities = new ArrayList<>();
        try {
            facilities = new CsvToBeanBuilder(new FileReader(csv))
                    .withType(Facility.class)
                    .build()
                    .parse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        facilities.forEach(System.out::println);
        return facilities;
    }
}
