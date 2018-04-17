package com.orioles.controller;

import com.orioles.constants.Party;
import com.orioles.model.User;
import com.orioles.persistence.UserRepository;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
  public User login(@RequestParam String email, @RequestParam String password){
    if(httpSession.getAttribute("user") != null){
      return null;
    }
    List<User> users = userRepository.findByEmailIgnoreCase(email);
    if(!users.isEmpty()){
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      User user = users.get(0);
      if(encoder.matches(password, user.getPassword())){
        httpSession.setAttribute("user", user);
        return user;
      }
      return null;
    }
    return null;
  }
  
  @RequestMapping("/logout")
  public String logout(){
    httpSession.invalidate();
    return "OK";
  }
  
  @PostMapping("/update")
  public String update(@RequestParam String newUsername, @RequestParam String newPassword,
          @RequestParam String newParty){
    User user = (User) httpSession.getAttribute("user");
    if(!(newUsername = newUsername.trim()).equals("")){
      user.setUsername(newUsername);
    }
    if(!(newPassword).equals("")){
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      user.setPassword(encoder.encode(newPassword));
    }
    switch(newParty){
      case "":
        break;
      case "0":
        user.setParty(Party.REPUBLICAN);
        break;
      case "1":
        user.setParty(Party.DEMOCRAT);
        break;
      case "2":
        user.setParty(Party.GREEN);
        break;
      case "3":
        user.setParty(Party.LIBERTARIAN);
        break;
      default:
        user.setParty(Party.OTHER);
        break;
    }
    userRepository.save(user);
    return "OK";
  }
}
