package com.orioles.controller;

import com.orioles.Security.PasswordUtility;
import com.orioles.exceptions.NoSuchUserException;
import com.orioles.model.User;
import com.orioles.persistence.UserRepository;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private Environment environment;

    @PostMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password){
        final String userAttribute = environment.getProperty("orioles.session.user");
        User user = (User)httpSession.getAttribute(userAttribute);
        if (user != null) {
            return user;
        }
        List<User> users = userRepository.findByUsername(username);
        if (!users.isEmpty()) {
            user = users.get(0);
            if (PasswordUtility.matches(password, user.getPassword())) {
                httpSession.setAttribute(userAttribute, user);
                return user;
            }
        }
        throw new NoSuchUserException(environment.getProperty("orioles.login.invalid"));
    }

    @RequestMapping("/logout")
    public String logout() {
        httpSession.invalidate();
        return environment.getProperty("orioles.statuscode.success");
    }
}
