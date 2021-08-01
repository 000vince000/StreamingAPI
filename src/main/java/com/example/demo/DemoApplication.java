package com.example.demo;

import com.example.demo.pojos.Facility;
import com.example.demo.pojos.Loan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;

@SpringBootApplication
public class DemoApplication {
	private final static String BANK_CSV = "small/banks.csv";
	private final static String LOAN_CSV = "small/loans.csv";
	private final static String COVENANT_CSV = "small/covenants.csv";
	private final static String FACILITY_CSV = "small/facilities.csv";

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		//initialization
		LoanService loanService = new LoanService(LOAN_CSV);
		BankService bankService = new BankService(BANK_CSV);
		CovenantService covenantService = new CovenantService(COVENANT_CSV);
		FacilityService facilityService = new FacilityService(FACILITY_CSV);

		List<Facility> facilities = facilityService.getFacilityList();
		List<Loan> loans = loanService.getLoanList();
		loans.forEach(l -> {
			System.out.println(l.getInterestRate());
		});
	}

}
