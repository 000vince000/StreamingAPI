package com.example.demo;

import com.example.demo.pojos.Covenant;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CovenantService {
    @Getter private List<Covenant> covenantList;
    private Map<Integer,List<String>> bannedStateMap;
    public CovenantService(String csv){
        covenantList = prepareCovenantsCsv(csv);
        bannedStateMap = new HashMap<>();
        covenantList.stream()
            .filter(covenant -> covenant.getBannedState()!= null)
            .forEach(covenant -> {
                List<String> temp = bannedStateMap.get(covenant.getFacilityId()) == null? new ArrayList<>():bannedStateMap.get(covenant.getFacilityId());
                temp.add(covenant.getBannedState());
                bannedStateMap.put(covenant.getFacilityId(), temp);
        });
    }

    public List<String> getBannedStatesByFacilityId(Integer facilityId){
        return bannedStateMap.get(facilityId);
    }

    private List<Covenant> prepareCovenantsCsv(String csv){
        List<Covenant> covenants = new ArrayList<>();
        try {
            covenants = new CsvToBeanBuilder(new FileReader(csv))
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
