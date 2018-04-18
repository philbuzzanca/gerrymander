package com.orioles.controller;

import com.orioles.Security.PasswordUtility;
import com.orioles.constants.Party;
import com.orioles.model.User;
import com.orioles.persistence.UserRepository;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private Environment environment;

    @PostMapping("/update")
    public String update(@RequestParam String newUsername,
            @RequestParam String newPassword,
            @RequestParam String newParty) {
        User user = (User) httpSession.getAttribute("user");
        if (!(newUsername = newUsername.trim()).isEmpty()) {
            user.setUsername(newUsername);
        }
        if (!newPassword.isEmpty()) {
            user.setPassword(PasswordUtility.encode(newPassword));
        }
        if (!newParty.isEmpty()) {
            int partyNum = Integer.parseInt(newParty);
            if (partyNum >= 0 && partyNum <= Party.values().length) {
                user.setParty(Party.values()[partyNum]);
            }
        }
        String response = environment.getProperty("orioles.statuscode.success");
        userRepository.save(user);
        return response;
    }
}
