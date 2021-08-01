package com.example.demo;

import com.example.demo.pojos.Covenant;
import com.example.demo.pojos.Facility;
import com.example.demo.pojos.Loan;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.Buffer;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class DemoApplication {
	private final static String LOAN_CSV = "small/loans.csv";

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		//initialization
//		LoanService loanService = new LoanService(LOAN_CSV);

		ApiController controller = new ApiController();

//		List<Facility> facilities = facilityService.getFacilityList();
//		List<Loan> loans = loanService.getLoanList();
//		List<Covenant> covenants = covenantService.getCovenantList();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(LOAN_CSV));
			reader.readLine(); // skip headers
			String csvLine;
			while((csvLine = reader.readLine()) != null) {
				System.out.println(String.format("parsing %s", csvLine));
				controller.sendCsvLine(csvLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//
//		loans.stream().forEach(loan -> {
//			List<Integer> facilityIds = facilities.stream()
//				.filter(facility -> facility.getInterestRate().compareTo(loan.getInterestRate()) < 0)
//				.map(Facility::getId)
//				.collect(Collectors.toList());
//			List<String> bannedStates = covenants.stream()
//				.filter(covenant -> facilityIds.contains(covenant.getFacilityId()))
//				.map(covenant -> covenant.getBannedState())
//				.collect(Collectors.toList());
//
//		});
	}

}
