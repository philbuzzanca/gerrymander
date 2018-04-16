package orioles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import orioles.persistence.UserRepository;
import orioles.model.User;

@Controller
public class RegisterController {
  @Autowired
  private UserRepository userRepository;
  
  @PostMapping("/register")
  public String register(@RequestParam String username, @RequestParam String email, @RequestParam String password){
    if(!userRepository.findByEmail(email).isEmpty()){
      return null;
    }
    userRepository.save(new User(username, email, password));
    return "OK";
  }
}
