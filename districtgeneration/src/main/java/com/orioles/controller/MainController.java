package com.orioles.controller;

import com.orioles.constants.Constants;
import com.orioles.districtgeneration.Constraint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class MainController {
    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("helpText", Constants.HELP);
        model.addAttribute("aboutText", Constants.ABOUT);
        return "index";
    }
}
