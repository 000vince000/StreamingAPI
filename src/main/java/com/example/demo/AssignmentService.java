package com.example.demo;

import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AssignmentService {
    private Writer writer;
    private final String ASSIGNMENT_CSV = "assignments.csv";
    public ICSVWriter csvWriter;

    public AssignmentService(){
        try {
            writer = Files.newBufferedWriter(Paths.get(ASSIGNMENT_CSV));
            // header record
            String[] headerRecord = {"loan_id", "facility_id"};
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

    public void writeAssignment(Integer loanId, Integer facilityId){
        csvWriter.writeNext(new String[] {loanId.toString(), facilityId.toString()});
    }

    protected void finalize() throws Throwable{
        try {
            csvWriter.close();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
