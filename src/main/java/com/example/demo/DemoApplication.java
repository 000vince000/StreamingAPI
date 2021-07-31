package com.example.demo;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class DemoApplication {
	private final String LOANS_CSV = "small/loans.csv";
	private final String COVENANTS_CSV = "small/covenants.csv";
	private final String FACILITIES_CSV = "small/facilities.csv";

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

//		try (Reader reader = Files.newBufferedReader(Paths.get("small/banks.csv"));
//			 CSVReader csvReader = new CSVReader(reader)) {
//
//			// read one record at a time
//			String[] record;
//			while ((record = csvReader.readNext()) != null) {
//				System.out.println("User["+ String.join(", ", record) +"]");
//			}
//
//		} catch (IOException | CsvValidationException ex) {
//			ex.printStackTrace();
//		}
	}

}
