package com.orioles.controller;

import com.orioles.security.PasswordUtility;
import com.orioles.constants.Party;
import com.orioles.model.User;
import com.orioles.persistence.UserRepository;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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

	@PostMapping("/register")
	public String register (@RequestParam String username,
							@RequestParam String password){
		if ((username = username.trim()).equals("")
				|| !userRepository.findByUsername(username).isEmpty()) {
			return null;
		}
		userRepository.save(new User(username, password));
		return "OK";
	}

	@PostMapping("/login")
	public User login(@RequestParam String username, @RequestParam String password) {
		if (httpSession.getAttribute("user") != null) {
			return null;
		}
		List<User> users = userRepository.findByUsername(username);
		if (!users.isEmpty()) {
			User user = users.get(0);
			if (PasswordUtility.matches(password, user.getPassword())) {
				httpSession.setAttribute("user", user);
				return user;
			}
			return null;
		}
		return null;
	}

    @PostMapping("/update")
    public String update(@RequestParam String newUsername,
            @RequestParam String newPassword,
            @RequestParam String newParty) {
        User user = (User) httpSession.getAttribute("user");
        if (!(newUsername = newUsername.trim()).equals("")) {
            user.setUsername(newUsername);
        }
        if (!(newPassword).equals("")) {
            user.setPassword(PasswordUtility.encode(newPassword));
        }
        if (!newParty.equals("")) {
            int partyNum = Integer.parseInt(newParty);
            if (partyNum >= 0 && partyNum <= Party.values().length) {
                user.setParty(Party.values()[partyNum]);
            }
        }
        
        userRepository.save(user);
        return "OK";
    }
    
    @RequestMapping("/logout")
    public String logout() {
        httpSession.invalidate();
        return "OK";
    }
}
