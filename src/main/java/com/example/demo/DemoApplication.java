package com.example.demo;

import com.example.demo.pojos.Bank;
import com.example.demo.pojos.Covenant;
import com.example.demo.pojos.Facility;
import com.example.demo.pojos.Loan;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {
	private final String LOANS_CSV = "small/loans.csv";
	private final String COVENANTS_CSV = "small/covenants.csv";
	private final String FACILITIES_CSV = "small/facilities.csv";

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		PrepareLoansCsv();
		PrepareCovenantsCsv();
		PrepareBanksCsv();
	}

	private static List<Loan> PrepareLoansCsv(){
		List<Loan> loans = new ArrayList<>();
		try {
			loans = new CsvToBeanBuilder(new FileReader("small/loans.csv"))
					.withType(Loan.class)
					.build()
					.parse();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		loans.forEach(System.out::println);
		return loans;
	}
	private static List<Bank> PrepareBanksCsv(){
		List<Bank> banks = new ArrayList<>();
		try {
			banks = new CsvToBeanBuilder(new FileReader("small/banks.csv"))
					.withType(Bank.class)
					.build()
					.parse();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		banks.forEach(System.out::println);
		return banks;
	}
	private static List<Facility> PrepareFacilitiesCsv(){
		List<Facility> facilities = new ArrayList<>();
		try {
			facilities = new CsvToBeanBuilder(new FileReader("small/facilities.csv"))
					.withType(Facility.class)
					.build()
					.parse();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		facilities.forEach(System.out::println);
		return facilities;
	}
	private static List<Covenant> PrepareCovenantsCsv(){
		List<Covenant> covenants = new ArrayList<>();
		try {
			covenants = new CsvToBeanBuilder(new FileReader("small/covenants.csv"))
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
