package com.example.demo;

import com.example.demo.pojos.Bank;
import com.example.demo.pojos.Covenant;
import com.example.demo.pojos.Facility;
import com.example.demo.pojos.Loan;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanService {
    @Getter private List<Loan> loanList;

    private BankService bankService;
    private CovenantService covenantService;
    private FacilityService facilityService;

    private List<Bank> banks;
    private List<Covenant> covenants;
    private List<Facility> facilities;

    public LoanService(String bankCsv, String covenantCsv, String facilityCsv){
        bankService = new BankService(bankCsv);
        covenantService = new CovenantService(covenantCsv);
        facilityService = new FacilityService(facilityCsv);
        banks = bankService.getBankList();
        covenants = covenantService.getCovenantList();
        facilities = facilityService.getFacilityList();
    }

    private List<Loan> prepareLoansCsv(String csv){
        List<Loan> loans = new ArrayList<>();
        try {
            loans = new CsvToBeanBuilder(new FileReader(csv))
                    .withType(Loan.class)
                    .build()
                    .parse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        loans.forEach(System.out::println);
        return loans;
    }
    public Facility sourceLoan(Loan loan) throws IllegalStateException {
        //1. get facility IDs from covenants where state is not banned and default rate < max default allowable
        Set<Integer> sanitizedFacilityIds = covenants.stream()
				.filter(covenant -> covenant.getBannedState() != loan.getState())
                .filter(covenant -> covenant.getMaxDefaultLikelihood() != null && covenant.getMaxDefaultLikelihood().compareTo(loan.getDefaultLikelihood()) > 0)
                .map(Covenant::getFacilityId)
				.collect(Collectors.toSet());

        //TODO need to keep track of all facility's running sum of loans
        //2. find facilities with lower interest rates
        Facility faciltyWithMinRate = facilities.stream()
                .filter(facility -> facility.getInterestRate().compareTo(loan.getInterestRate()) < 0)
                .filter(facility -> sanitizedFacilityIds.contains(facility.getId()))
                .collect(Collectors.minBy(Comparator.comparing(Facility::getInterestRate)))
                .orElseThrow(() -> new IllegalStateException("no suitable facility is found"));

        return faciltyWithMinRate;
    }

    public BigDecimal calculateYield(Loan loan, BigDecimal facilityInterestRate) throws IllegalStateException{
        BigDecimal a = (new BigDecimal(1).subtract(loan.getDefaultLikelihood()));
        BigDecimal c = a.multiply(loan.getInterestRate()).multiply(loan.getAmount());
        BigDecimal d = c.subtract(loan.getDefaultLikelihood().multiply(loan.getAmount()));
        BigDecimal expectedYield = d.subtract(facilityInterestRate.multiply(loan.getAmount()));

        if(expectedYield.compareTo(new BigDecimal(0)) < 0) throw new IllegalStateException("no yield should be negative");

        return expectedYield;
    }
}
