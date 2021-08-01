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
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FacilityService {
    @Getter private List<Facility> facilityList;
    private Map<Integer, BigDecimal> maxCapacityMap;
    private Map<Integer, BigDecimal> workingCapacityMap;
    private Map<Integer, BigDecimal> runningYieldMap;
    private final String YIELD_CSV = "yields.csv";

    public FacilityService(String csv){
        facilityList = prepareFacilitiesCsv(csv);
        maxCapacityMap = facilityList.stream().collect(Collectors.toMap(Facility::getId,Facility::getAmount));
        workingCapacityMap = facilityList.stream().collect(Collectors.toMap(Facility::getId,Facility::getAmount));
        runningYieldMap = new HashMap<>();
    }

    public BigDecimal getWorkingCapacityByFacilityId(Integer facilityId){
        return workingCapacityMap.get(facilityId);
    }

    public BigDecimal adjustWorkingCapacityByFacilityId(Integer facilityId, BigDecimal adjustment){
        BigDecimal adjustedCapcity = workingCapacityMap.get(facilityId).subtract(adjustment);
        System.out.println(String.format("facility %d's capacity is reduced from %.2f to %.2f", facilityId, workingCapacityMap.get(facilityId), adjustedCapcity));
        workingCapacityMap.put(facilityId, adjustedCapcity);
        return adjustedCapcity;
    }

    public BigDecimal adjustRunningYieldMap(Integer facilityId, BigDecimal yield){
        BigDecimal currentYield = runningYieldMap.get(facilityId);
        BigDecimal adjustedRunningYield =  currentYield !=null ? currentYield.add(yield) : yield;
        System.out.println(String.format("facility %d's running yield is augmented from %.2f to %.2f", facilityId, currentYield==null?0:currentYield, adjustedRunningYield));
        runningYieldMap.put(facilityId,adjustedRunningYield);
        return adjustedRunningYield;
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

    //destructor: write the finalized yields into CSV
    protected void finalize(){
        writeYields();
    }

    private void writeYields(){
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

            //write results
            runningYieldMap.entrySet().forEach(set -> {
                csvWriter.writeNext(new String[] {set.getKey().toString(), Integer.toString(set.getValue().round(new MathContext(3, RoundingMode.CEILING)).intValue())});
            });

            csvWriter.close();
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
