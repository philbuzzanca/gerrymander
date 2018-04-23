package com.orioles.controller;

import com.orioles.model.Demographics;
import com.orioles.persistence.DemographicsRepository;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DemographicsController {
    @Autowired
    HttpSession httpSession;
    @Autowired
    DemographicsRepository demographicsRepository;
    
    @PostMapping("/compareDistricts")
    public List<Demographics> getDistricts(@RequestParam String d1, @RequestParam String d2){
        List<Demographics> districts = new ArrayList<>();
        districts.add(demographicsRepository.findByCdIDIgnoreCase(d1));
        districts.add(demographicsRepository.findByCdIDIgnoreCase(d2));
        return districts;
    }
}
