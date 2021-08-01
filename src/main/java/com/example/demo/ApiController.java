package com.example.demo;

import com.example.demo.pojos.Facility;
import com.example.demo.pojos.Loan;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.Charset;


@RequestMapping("/api/stream")
@RestController
public class ApiController {
    private final static String BANK_CSV = "small/banks.csv";
    private final static String COVENANT_CSV = "small/covenants.csv";
    private final static String FACILITY_CSV = "small/facilities.csv";

    LoanService loanService = new LoanService(BANK_CSV,COVENANT_CSV,FACILITY_CSV);

    @PostMapping(value="/csv")
    public void sendCsvLine(@RequestParam("csvLine") String csvLine) {
        final Integer INTEREST_RATE = 0;
        final Integer AMOUNT = 1;
        final Integer ID = 2;
        final Integer DEFAULT_LIKELIHOOD = 3;
        final Integer STATE = 4;

        InputStream is = new ByteArrayInputStream(csvLine.getBytes());
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        try {
            String[] parsedFields = br.readLine().split(",");
            Loan loan = new Loan();
            loan.setInterestRate(new BigDecimal(parsedFields[INTEREST_RATE]));
            loan.setAmount(new BigDecimal(parsedFields[AMOUNT]));
            loan.setId(new Integer(parsedFields[ID]));
            loan.setDefaultLikelihood(new BigDecimal(parsedFields[DEFAULT_LIKELIHOOD]));
            loan.setState(parsedFields[STATE]);

            System.out.println(String.format("stream received: %s", loan));
            Facility sourcedFacility = loanService.sourceLoan(loan);
            AssignmentService.writeAssignment(loan.getId(), sourcedFacility.getId());
            BigDecimal yield = loanService.calculateYield(loan, sourcedFacility.getInterestRate());
            System.out.println(String.format("==> yield calculated: %.2f", yield));

        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/csv")
    public ResponseEntity<StreamingResponseBody> getCsvFile() {
        StreamingResponseBody stream = output -> {
            BufferedWriter bw = null;
            try {
                Writer w = new OutputStreamWriter(System.out);
                bw = new BufferedWriter(w);
                bw.write("output test");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=small/rcv_banks.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(stream);
    }


}
