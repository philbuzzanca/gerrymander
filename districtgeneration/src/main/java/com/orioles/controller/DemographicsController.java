/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.orioles.controller;

import com.orioles.model.Demographics;
import com.orioles.persistence.DemographicsRepository;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemographicsController {
    
    @Autowired
            HttpSession httpSession;
    @Autowired
            DemographicsRepository demographicsRepository;
    
    @PostMapping("/compareDistricts")
    public Demographics[] getDistricts(@RequestParam String d1, @RequestParam String d2){
        Demographics[] d = new Demographics[2];
        d[0] = demographicsRepository.findByCdIDIgnoreCase(d1);
        d[1] = demographicsRepository.findByCdIDIgnoreCase(d2);
        return d;
    }
}
