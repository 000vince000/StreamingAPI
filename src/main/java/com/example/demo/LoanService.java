package com.example.demo;

import com.example.demo.pojos.Bank;
import com.example.demo.pojos.Covenant;
import com.example.demo.pojos.Facility;
import com.example.demo.pojos.Loan;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanService {
//    @Getter private List<Loan> loanList;

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

    public Optional<Facility> sourceLoan(Loan loan) throws IllegalStateException {
        //1. get facility IDs from covenants where state is not banned and default rate < max default allowable
        Set<Integer> sanitizedFacilityIds = covenants.stream()
                .filter(covenant -> covenant.getMaxDefaultLikelihood() != null ?
                        covenant.getMaxDefaultLikelihood().compareTo(loan.getDefaultLikelihood()) > 0 : true)
                .map(Covenant::getFacilityId)
				.collect(Collectors.toSet());

        //2. find facilities with lower interest rates and where there is spare loan capacity
        Optional<Facility> faciltyWithMinRate = facilities.stream()
                .filter(facility -> facility.getInterestRate().compareTo(loan.getInterestRate()) < 0)
                .filter(facility -> !covenantService.getBannedStatesByFacilityId(facility.getId()).contains(loan.getState())) //TODO
                .filter(facility -> sanitizedFacilityIds.contains(facility.getId()))
                .filter(facility -> loan.getAmount().compareTo(facilityService.getWorkingCapacityByFacilityId(facility.getId())) < 0)
                .collect(Collectors.minBy(Comparator.comparing(Facility::getInterestRate)));
                //.orElseThrow(() -> new IllegalStateException("no suitable facility is found"));

        BigDecimal adjustedCapacity;
        if(faciltyWithMinRate.isPresent()){
            adjustedCapacity = facilityService.adjustWorkingCapacityByFacilityId(faciltyWithMinRate.get().getId(), loan.getAmount());
        } else {
            System.out.println("no suitable facility is found for loan id=" + loan.getId());
            adjustedCapacity = new BigDecimal(0);
        }

        if (adjustedCapacity.compareTo(new BigDecimal(0)) < 0) throw new IllegalArgumentException("facility's capacity should never be negative");

        return faciltyWithMinRate;
    }

    public BigDecimal calculateYield(Loan loan, Facility facility) throws IllegalStateException{
        BigDecimal a = (new BigDecimal(1).subtract(loan.getDefaultLikelihood()));
        BigDecimal c = a.multiply(loan.getInterestRate()).multiply(loan.getAmount());
        BigDecimal d = c.subtract(loan.getDefaultLikelihood().multiply(loan.getAmount()));
        BigDecimal expectedYield = d.subtract(facility.getInterestRate().multiply(loan.getAmount()));

        if(expectedYield.compareTo(new BigDecimal(0)) < 0) throw new IllegalStateException("no yield should be negative");
        facilityService.adjustRunningYieldMap(facility.getId(), expectedYield);
        return expectedYield;
    }
}
