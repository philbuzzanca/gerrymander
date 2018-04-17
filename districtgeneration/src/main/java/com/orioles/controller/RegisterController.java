package com.orioles.controller;

import com.orioles.constants.Party;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.orioles.persistence.UserRepository;
import com.orioles.model.User;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class RegisterController {
  @Autowired
  private UserRepository userRepository;
  
  @PostMapping("/register")
  public String register(@RequestParam String username, @RequestParam String email, @RequestParam String password){
    if((username = username.trim()).equals("")){
      return null;
    }
    List<User> users = userRepository.findByEmailIgnoreCase(email);
    if(!users.isEmpty()){
      return null;
    }
    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(password));
    user.setParty(Party.OTHER);
    userRepository.save(user);
    return "OK";
  }
}
