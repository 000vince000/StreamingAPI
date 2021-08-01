package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
public class DemoApplication {
//	private static boolean isSmallLoad = true;
	private final static String LOAN_CSV = "large/loans.csv";

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		//initialization
		ApiController controller = new ApiController();

		//simulate a stream input by calling the stream API in a tight loop
		try {
			BufferedReader reader = new BufferedReader(new FileReader(LOAN_CSV));
			reader.readLine(); // skip headers
			String csvLine;
			while((csvLine = reader.readLine()) != null) {
//				System.out.println(String.format("parsing %s", csvLine));
				controller.sendCsvLine(csvLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
