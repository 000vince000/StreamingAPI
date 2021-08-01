package com.example.demo;

import com.example.demo.pojos.Facility;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;
import lombok.Setter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FacilityService {
    @Getter private List<Facility> facilityList;
    private Map<Integer, BigDecimal> maxCapacityMap;
    private Map<Integer, BigDecimal> workingCapacityMap;
    private final String YIELD_CSV = "yields.csv";

    public FacilityService(String csv){
        facilityList = prepareFacilitiesCsv(csv);
        maxCapacityMap = facilityList.stream().collect(Collectors.toMap(Facility::getId,Facility::getAmount));
        workingCapacityMap = facilityList.stream().collect(Collectors.toMap(Facility::getId,null));
    }

    public BigDecimal getWorkingCapacityByFacilityId(Integer facilityId){
        return workingCapacityMap.get(facilityId);
    }
    public BigDecimal adjustWorkingCapacityByFacilityId(Integer facilityId, BigDecimal adjustment){
        BigDecimal adjustedCapcity = workingCapacityMap.get(facilityId).subtract(adjustment);
        workingCapacityMap.put(facilityId, adjustedCapcity);
        return adjustedCapcity;
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
    public void writeYields(){
        Writer writer;
        ICSVWriter csvWriter;

        try {
            writer = Files.newBufferedWriter(Paths.get(YIELD_CSV));
            // header record
            String[] headerRecord = {"facility_id","expected_yield"};
            // create a csv writer
            csvWriter = new CSVWriterBuilder(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .build();

            // write header record
            csvWriter.writeNext(headerRecord);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
