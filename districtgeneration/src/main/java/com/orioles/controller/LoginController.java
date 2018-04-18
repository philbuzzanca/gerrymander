package com.orioles.controller;

import com.orioles.security.PasswordUtility;
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
public class LoginController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private HttpSession httpSession;

	@PostMapping("/login")
	public User login(@RequestParam String email, @RequestParam String password) {
		if (httpSession.getAttribute("user") != null) {
			return null;
		}
		List<User> users = userRepository.findByEmailIgnoreCase(email);
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

	@RequestMapping("/logout")
	public String logout() {
		httpSession.invalidate();
		return "OK";
	}
}
