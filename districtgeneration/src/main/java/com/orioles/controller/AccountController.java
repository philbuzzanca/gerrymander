package com.orioles.controller;

import com.orioles.exceptions.NoSuchUserException;
import com.orioles.security.PasswordUtility;
import com.orioles.constants.Party;
import com.orioles.model.User;
import com.orioles.persistence.UserRepository;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@PostMapping("/register")
	public String register (@RequestParam String username, @RequestParam String password){
		if ((username = username.trim()).isEmpty()
				|| !userRepository.findByUsername(username).isEmpty()) {
			return null;
		}
		userRepository.save(new User(username, password));
		return environment.getProperty("orioles.statuscode.success");
	}

	@PostMapping("/login")
	public User login(@RequestParam String username, @RequestParam String password) {
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
        userRepository.save(user);
        return "OK";
    }
    
    @GetMapping("/getUsers")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }
    
    @RequestMapping("/logout")
    public String logout() {
        httpSession.invalidate();
        return environment.getProperty("orioles.statuscode.success");
    }
}
