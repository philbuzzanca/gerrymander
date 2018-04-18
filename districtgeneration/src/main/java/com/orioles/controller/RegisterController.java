package com.orioles.controller;

import com.orioles.security.PasswordUtility;
import com.orioles.constants.Party;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.orioles.persistence.UserRepository;
import com.orioles.model.User;
import java.util.List;

@RestController
public class RegisterController {
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/register")
	public String register (@RequestParam String username,
							@RequestParam String password){
		if ((username = username.trim()).equals("")) {
			return null;
		}
		List<User> users = userRepository.findByUsername(username);
		if (!users.isEmpty()) {
			return null;
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(PasswordUtility.encode(password));
		user.setParty(Party.OTHER);
		userRepository.save(user);
		return "OK";
	}
}

